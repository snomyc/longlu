package com.longlu.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;

import net.sf.json.JSONArray;

public class JsonUtil {
	private Map auths;
	
	public Map getAuths() {
		return auths;
	}
	public void setAuths(Map auths) {
		this.auths = auths;
	}
	public JsonUtil(Map auths){
		this.auths=auths;
	}
  public boolean hasAuths(String key){
      if(auths.containsKey(key)) return true;
      else return false;
   }
  
  public String strAuths(){
	  JSONArray json = JSONArray.fromObject(auths);
	  String sjson = json.toString().replaceAll(",", "},{");
	  return json.toString();
  }
  
  /** 
   * 默认编码utf -8 
   * Obtains character set of the entity, if known. 
   *  
   * @param entity must not be null 
   * @return the character set, or null if not found 
   * @throws ParseException if header elements cannot be parsed 
   * @throws IllegalArgumentException if entity is null 
   */    
  public static String getContentCharSet(final HttpEntity entity)   
      throws ParseException {   
 
      if (entity == null) {   
          throw new IllegalArgumentException("HTTP entity may not be null");   
      }   
      String charset = null;   
      if (entity.getContentType() != null) {    
          HeaderElement values[] = entity.getContentType().getElements();   
          if (values.length > 0) {   
              NameValuePair param = values[0].getParameterByName("charset" );   
              if (param != null) {   
                  charset = param.getValue();   
              }   
          }   
      }
       
      if(StringUtils.isEmpty(charset)){  
          charset = "UTF-8";  
      }  
      return charset;   
  }
  
  /** 
       * 将JAVA对象转换成JSON字符串 
      * @param obj 
       * @return 
       * @throws IllegalArgumentException 
       * @throws IllegalAccessException 
      */  
    public static String simpleObjectToJsonStr(Object obj) throws IllegalArgumentException, IllegalAccessException{  
         if(obj==null){  
          return "null";  
       }  
        String jsonStr = "{";  
        Class<?> cla = obj.getClass();  
        Field fields[] = cla.getDeclaredFields();  
        for (Field field : fields) {  
            field.setAccessible(true);  
            if(field.getType() == long.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getLong(obj)+",";  
            }else if(field.getType() == double.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getDouble(obj)+",";  
            }else if(field.getType() == float.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getFloat(obj)+",";  
            }else if(field.getType() == int.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getInt(obj)+",";  
            }else if(field.getType() == boolean.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getBoolean(obj)+",";  
            }else if(field.getType() == Integer.class||field.getType() == Boolean.class  
                    ||field.getType() == Double.class||field.getType() == Float.class                     
                    ||field.getType() == Long.class){                 
                jsonStr += "\""+field.getName()+"\":"+field.get(obj)+",";  
            }else if(field.getType() == String.class){  
                jsonStr += "\""+field.getName()+"\":\""+field.get(obj)+"\",";  
            } 
       }  
        jsonStr = jsonStr.substring(0,jsonStr.length()-1);  
        jsonStr += "}";  
            return jsonStr;       
    } 

}
