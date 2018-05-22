package com.longlu.util.pagination.interceptor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.longlu.util.pagination.Dialect;
import com.longlu.util.pagination.MySql5Dialect;
import com.longlu.util.pagination.OracleDialect;
import com.longlu.util.pagination.Pagination;
import com.longlu.util.pagination.PaginationContext;
import com.longlu.util.pagination.ReflectHelper;
import com.longlu.util.pagination.ReflectUtil;

/**
 * 分页拦截器
 * @author LiCheng、ZhangQin
 * 只拦截query部分，支持EasyUI
 */
@Intercepts({@Signature(type=Executor.class,method="query",args={ MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })})
public class PaginationInterceptor implements Interceptor{
	
	private final static Log log = LogFactory.getLog(PaginationInterceptor.class);   
	
	Dialect dialect = new MySql5Dialect();
	 
	public Object intercept(Invocation invocation) throws Throwable {
		
		MappedStatement mappedStatement=(MappedStatement)invocation.getArgs()[0];		
		Object parameter = invocation.getArgs()[1]; 
		BoundSql boundSql = mappedStatement.getBoundSql(parameter); 
		String originalSql = boundSql.getSql().trim();   
		RowBounds rowBounds = (RowBounds)invocation.getArgs()[2];

		Object parameterObject = boundSql.getParameterObject();
		if(null==boundSql || null==boundSql.getSql() || "".equals(boundSql.getSql())){
			return null;
		}
		//分页对象
		Pagination pagination=null;
		//分页上下文
		PaginationContext context=PaginationContext.getContext();
		//map传参每次都将currentPage重置,先判读map再判断context
		if(parameterObject!=null){
			pagination=(Pagination)ReflectHelper.isPage(parameterObject,"pagination");
		}
		if(pagination!=null&&pagination.isEnable()){
			//得到总记录数
			int total=pagination.getTotal();
			if(total==0){
				StringBuffer countSql  = new StringBuffer(originalSql.length()+100 );
				countSql.append("select count(1) from (").append(originalSql).append(") t");
				Connection connection=mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
                PreparedStatement countStmt = connection.prepareStatement(countSql.toString());  
                BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql.toString(),boundSql.getParameterMappings(),parameterObject);  
                Field metaParamsField = ReflectUtil.getFieldByFieldName(boundSql, "metaParameters");
                if (metaParamsField != null) {
                    MetaObject mo = (MetaObject) ReflectUtil.getValueByFieldName(boundSql, "metaParameters");
                    ReflectUtil.setValueByFieldName(countBS, "metaParameters", mo);
                }
                setParameters(countStmt,mappedStatement,countBS,parameterObject);  
                ResultSet rs = countStmt.executeQuery();  
                if (rs.next()) {  
                	total = rs.getInt(1);  
                }  
                rs.close();
                countStmt.close();  
                connection.close();
			}
		    //分页计算
			pagination.init(total,pagination.getRows());
			if(rowBounds == null || rowBounds == RowBounds.DEFAULT){
				rowBounds= new RowBounds(pagination.getRows()*(pagination.getPage()-1),pagination.getRows());
			}
			//分页查询 本地化对象 修改数据库注意修改实现
		    String pagesql=dialect.getLimitString(originalSql, rowBounds.getOffset(), rowBounds.getLimit());
		    invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
		    Object obj=getNewParameterObject(mappedStatement,boundSql);	
		    BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), pagesql,boundSql.getParameterMappings(),obj);   
		    
		    MappedStatement newMs = copyFromMappedStatement(mappedStatement,new BoundSqlSqlSource(newBoundSql));  
            
		    invocation.getArgs()[0]= newMs;  
		}
		return invocation.proceed();
	}
	
	public static class BoundSqlSqlSource implements SqlSource {  
        BoundSql boundSql;  
        public BoundSqlSqlSource(BoundSql boundSql) {  
            this.boundSql = boundSql;  
        }  
        public BoundSql getBoundSql(Object parameterObject) {  
            return boundSql;  
        }  
    }
	
	public Object plugin(Object arg0) {
		 return Plugin.wrap(arg0, this);
	}
	
	public void setProperties(Properties arg0) {
	        
	}
    
    /** 
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler 
     * @param ps 
     * @param mappedStatement 
     * @param boundSql 
     * @param parameterObject 
     * @throws SQLException 
     */  
    private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {  
    	ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());  
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();  
        if (parameterMappings != null) {  
            Configuration configuration = mappedStatement.getConfiguration();  
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();  
            MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);  
            for (int i = 0; i < parameterMappings.size(); i++) {  
                ParameterMapping parameterMapping = parameterMappings.get(i);  
                if (parameterMapping.getMode() != ParameterMode.OUT) {  
                    Object value;  
                    String propertyName = parameterMapping.getProperty();  
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);  
                    if (parameterObject == null) {  
                        value = null;  
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {  
                        value = parameterObject;  
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {  
                        value = boundSql.getAdditionalParameter(propertyName);  
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {  
                        value = boundSql.getAdditionalParameter(prop.getName());  
                        if (value != null) {  
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));  
                        }  
                    } else {  
                        value = metaObject == null ? null : metaObject.getValue(propertyName);  
                    }  
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();  
                    if (typeHandler == null) {  
                        throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());  
                    }  
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());  
                }  
            }  
        }  
    }  

    /**
     * 获得新ParameterObject对象，以对FOREACH支持
     * @author ZhangQin
     * @param mappedStatement MappedStatement对象
     * @param boundSql BoundSql对象
     * @return 参数集合
     * @throws SQLException SQL异常
     */
    private Object getNewParameterObject(MappedStatement mappedStatement,BoundSql boundSql) throws SQLException {  
    	HashMap<String,Object> paramsMap=(HashMap<String,Object>)boundSql.getParameterObject();
    	Object parameterObject=boundSql.getParameterObject();
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());  
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();  
        if (parameterMappings != null) {  
            Configuration configuration = mappedStatement.getConfiguration();  
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();  
            MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);  
            for (int i = 0; i < parameterMappings.size(); i++) {  
                ParameterMapping parameterMapping = parameterMappings.get(i);  
                if (parameterMapping.getMode() != ParameterMode.OUT) {  
                    Object value;  
                    String propertyName = parameterMapping.getProperty();  
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);  
                    if (parameterObject == null) {  
                        value = null;  
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {  
                        value = parameterObject;  
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {  
                        value = boundSql.getAdditionalParameter(propertyName);  
                        
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {  
                        value = boundSql.getAdditionalParameter(prop.getName());  
                        if (value != null) {  
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));  
                        }
                    } else {  
                        value = metaObject == null ? null : metaObject.getValue(propertyName);  
                    }  
                    paramsMap.put(propertyName, value);
                }  
            }  
        }
        return paramsMap;
    }  
    
    private MappedStatement copyFromMappedStatement(MappedStatement ms,   
    		 SqlSource newSqlSource) {   
		Builder builder = new MappedStatement.Builder(ms.getConfiguration(),   
		ms.getId(), newSqlSource, ms.getSqlCommandType());   
		builder.resource(ms.getResource());   
		builder.fetchSize(ms.getFetchSize());   
		builder.statementType(ms.getStatementType());   
		builder.keyGenerator(ms.getKeyGenerator());  
		if(ms.getKeyProperties() != null && ms.getKeyProperties().length > 0)
		{//TODO 单属性改为数组，检查错误情况
			builder.keyProperty(ms.getKeyProperties()[0]);
		}
		builder.timeout(ms.getTimeout());   
		builder.parameterMap(ms.getParameterMap());   
		builder.resultMaps(ms.getResultMaps());   
		builder.cache(ms.getCache());   
		MappedStatement newMs = builder.build();   
		return newMs;   
    }   
     
}