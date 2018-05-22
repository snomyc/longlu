package com.longlu.service.impl;

import org.springframework.stereotype.Service;
import com.longlu.service.KdtApiService;
/**     
* 类描述：  有赞第三方服务对接接口实现
* 创建人：yagncan   
* 创建时间：2016年12月13日 下午9:21:49     
*/
@Service
public class KdtApiServiceImpl implements KdtApiService{
//	
//	private static Logger logger = LoggerFactory.getLogger(KdtApiServiceImpl.class);
//	private KdtApiClient kdtApiClient;
//	private HttpResponse response;
//	
//	public String resultKdtApiToGet(String method,HashMap<String, String> params) {
//		try {
//			kdtApiClient = new KdtApiClient(KdtApiItem.APP_ID,KdtApiItem.APP_SECRET);
//			for (int i = 0; i < 5; i++) {
//				response = kdtApiClient.get(method, params);
//				//如果返回200，响应成功
//				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//					//取得返回的字符串
//		        	//取得返回的字符串并且读入到Reader流并输出到StringBuffer里
//					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//					StringBuffer result = new StringBuffer();
//					String line = "";
//					while ((line = bufferedReader.readLine()) != null) {
//						result.append(line);
//					}
//					return result.toString();
//				}
//				logger.error("调用有赞API失败【"+(i+1)+"】次");
//			}
//			
//		} catch (Exception e) {
//			logger.error("有赞API客户端连接失败...");
//			logger.error(e.getMessage(),e);
//		}
//		return null;
//	}
//	
//	public String resultKdtApiToPost(String method,HashMap<String, String> params, List<String> filePaths,String fileKey) {
//		try {
//			kdtApiClient = new KdtApiClient(KdtApiItem.APP_ID,KdtApiItem.APP_SECRET);
//			//如果5次都调用失败
//			for (int i = 0; i < 5; i++) {
//				response = kdtApiClient.post(method, params, filePaths, fileKey);
//				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//					StringBuffer result = new StringBuffer();
//					String line = "";
//					while ((line = bufferedReader.readLine()) != null) {
//						result.append(line);
//					}
//					return result.toString();
//				}
//				logger.error("调用有赞API失败【"+(i+1)+"】次");
//			}
//			logger.error("调用有赞API5次失败...");
//		} catch (Exception e) {
//			logger.error("有赞API客户端连接失败...");
//			logger.error(e.getMessage(),e);
//		}
//		return null;
//	}
//	
//	
//	
//	public String resultKdtApiToGetToOther(String app_id,String app_secret,String method,HashMap<String, String> params) {
//		try {
//			kdtApiClient = new KdtApiClient(app_id,app_secret);
//			for (int i = 0; i < 5; i++) {
//				response = kdtApiClient.get(method, params);
//				//如果返回200，响应成功
//				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//					//取得返回的字符串
//		        	//取得返回的字符串并且读入到Reader流并输出到StringBuffer里
//					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//					StringBuffer result = new StringBuffer();
//					String line = "";
//					while ((line = bufferedReader.readLine()) != null) {
//						result.append(line);
//					}
//					return result.toString();
//				}
//				logger.error("调用有赞API失败【"+(i+1)+"】次");
//			}
//			
//		} catch (Exception e) {
//			logger.error("有赞API客户端连接失败...");
//			logger.error(e.getMessage(),e);
//		}
//		return null;
//	}
	
	
//	public String listMaintances() throws Exception {
//		
//		String strOrder = "createtime desc";
//		//whereSql不能为空，不然会报404错误
//		this.whereSql = " ";
//		if(this.maintancebean!=null) {
//			
//			if(this.maintancebean.getMyid()!=null) {
//				this.whereSql += " and myid = "+this.maintancebean.getMyid();
//			}
//		}
//		
//		//进行转码
//		this.whereSql = URLEncoder.encode(this.whereSql, "UTF-8");
//		strOrder = URLEncoder.encode(strOrder, "UTF-8");
//		
//		//定义一个客户端对象
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		try {
//			//访问服务端的接口方法
//			String url_count = URIMap.getURIMap()+"/maintanceService/getCount/"+whereSql;
//			//特别注意webService中方法@get就用get,@post就用post不然会出现javax.ws.rs.ClientErrorException
//			HttpGet httpGet = new HttpGet(url_count);
//			httpGet.addHeader("ACCEPT", "application/json");
//	        //请求HttpClient，取得HttpResponse  
//	        HttpResponse httpResponse = httpclient.execute(httpGet);  
//	        //请求成功  
//	        String charset = "UTF-8";
//            String strTotalcount = "";
//            //如果响应没有问题的话
//            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)  
//            {  
//                //取得返回的字符串  
//            	HttpEntity entity_count = httpResponse.getEntity(); 
//           	    
//           	    if (entity_count != null) {
//                    charset = JsonUtil.getContentCharSet(entity_count);  
//                    // 使用EntityUtils的toString方法，传递编码，默认编码是ISO-8859-1   
//                    strTotalcount = EntityUtils.toString(entity_count, charset);   
//                }
//               JSONObject jo_count = JSONObject.fromObject(strTotalcount);
//               JSONObject root_count =jo_count.getJSONObject("root");
//               strTotalcount = root_count.get("totalCount").toString();
//            }
//	        
//	        //分页工具类
//            this.pagination = new Pagination(page,Integer.valueOf(strTotalcount),this.limit);
//            //调用url
//            String url = URIMap.getURIMap()+"/maintanceService/findByPage/"+whereSql+"/"+strOrder+"/"+this.pagination.getRowFrom()+"/"+ this.pagination.getRowTo();
//	        //将url放入get对象
//            httpGet = new HttpGet(url);
//            //增加头信息
//            httpGet.addHeader("ACCEPT", "application/json");
//	        //请求HttpClient，取得HttpResponse
//	        httpResponse = httpclient.execute(httpGet);  
//	        //请求成功  
//	        //响应成功
//	        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//	        	
//	        	//取得返回的字符串
//	        	HttpEntity entity = httpResponse.getEntity();
//	        	String strResult = "";
//	        	if(entity !=null) {
//	        		charset = JsonUtil.getContentCharSet(entity);
//	        		// 使用EntityUtils的toString方法，传递编码，默认编码是ISO-8859-1 
//	        		strResult = EntityUtils.toString(entity,charset);
//	        	}
//	        	//转换成object类型
//	        	JSONObject jo = JSONObject.fromObject(strResult);
//	        	//获得root节点
//	        	JSONObject root = jo.getJSONObject("root");
//	        	//获得root节点下的basicdata节点
//	        	JSONArray jarray = JSONArray.fromObject(root.get("maintance"));
//	        	//json转换为List方法
//	        	maintancelist = JSONArray.toList(jarray, new MaintanceBean(), new JsonConfig());
//	        	this.pagination.setDatas(maintancelist);
//	        }else {
//	        	;
//	        }
//	    }finally{
//	        httpclient.close();
//	    }
//        this.setSuccess(true);
//		return SUCCESS;
//		
//	}
//	
//	
//	/**
//	 * 添加/修改基础数据
//	 * @throws  
//	 * @throws  
//	 * */
//	
//	public String saveOrUpdateMaintance() throws Exception {
//		
//		
//		//判断是添加/修改
//		SystemLogs log = new SystemLogs();	
//		//添加
//		if(this.maintancebean.getCreatename().equals("")&&this.maintancebean.getCreatename().trim().length()==0) {
//			//获得用户名
//			request = ServletActionContext.getRequest();
//			HttpSession  session = request.getSession();
//			UserBean user = (UserBean)session.getAttribute("UserSession");
//			
//			if(user.getUsername()==null) {
//				this.setSuccess(false);
//				return SUCCESS;
//			}
//			this.maintancebean.setCreatename(user.getUsername());
//			log.setContent("添加数据成功!");
//			saveLog(log);
//		}
//		
//		//获得当前时间
//		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		String createtime = dformat.format(new Date()); 
//		maintancebean.setCreatetime(createtime);
//		//把basicbean转换成Json字符串
//		String jsonStr = JsonUtil.simpleObjectToJsonStr(maintancebean);
//		// 将JSON进行UTF-8编码,以便传输中文
//		String encoderJson = URLEncoder.encode(jsonStr, "UTF-8");
//		//定义一个客户端对象
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		try {
//			//访问服务端url方法
//			String url = URIMap.getURIMap()+"/maintanceService/saveOrupdate/";
//			HttpPost httpPost = new HttpPost(url);
//			httpPost.addHeader("ACCEPT", "application/json");
//            StringEntity se = new StringEntity(encoderJson);
//            se.setContentType(Constants.CONTENT_TYPE_TEXT_JSON);
//            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, Constants.APPLICATION_JSON));
//            //将se通过post的方式提交到服务端，调用服务端的方法
//            httpPost.setEntity(se);
//            httpclient.execute(httpPost);
//			
//            //如果处理为已处理
//            if(this.maintancebean.getMaintancestatus() == "已处理"||"已处理".equals(this.maintancebean.getMaintancestatus())) {
//            	//售后处理中的处理结果 （未完成，完成）改变 顺便改变售后登记的处理结果
//                String status = URLEncoder.encode(this.maintancebean.getMaintancestatus(),"UTF-8");
//                String url_status = URIMap.getURIMap()+"/servicecheckinService/changeStatusfindById/"+status+"/"+this.maintancebean.getMyid();
//                HttpGet httpGet = new HttpGet(url_status);
//    	        httpGet.addHeader("ACCEPT", "application/json");
//    	        //客户端get请求 执行
//    	        httpclient.execute(httpGet);
//            }
//            
//		}finally {
//			//关闭客户端对象
//			httpclient.close();
//		}
//		log.setContent("修改数据 id为:"+maintancebean.getId());
//		saveLog(log);
//		this.setSuccess(true);
//		return SUCCESS;
//	}
	
	
	
}
