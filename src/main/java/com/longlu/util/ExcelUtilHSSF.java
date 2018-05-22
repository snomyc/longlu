package com.longlu.util;


import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.longlu.util.excel.ExcelFormat;

public class ExcelUtilHSSF {
	
		private static Logger log = LogManager.getLogger(ExcelUtilHSSF.class);
		private HSSFWorkbook wb;
		private HSSFSheet sheet;
		private HSSFRow row;
		
		public static ExcelUtilHSSF getInstance() {
			return new ExcelUtilHSSF();
		}

		
		/**
		 * @Title exportExcelOutputStreamByReflect
		 * @param @param objList 需要导出的list<? extends Object>数据
		 * @param @param title excel标题用逗号隔开 格式如: "xx,yy,zz"
		 * @param @param field excel导出字段用逗号隔开 格式如:"xx,yy,zz"，字段属性必须与标题含义一致  注:如果field是对象的话需要取对象的值则用. 即: 对象.属性
		 * @param @param changeAttr 即需要转换的格式化工具类,如果是需要映射相应的枚举类则应注意以下问题
		 * 1.new ExcelFormatMapper(DiscardEnum.class,"value")  value为 枚举中获取值的属性字段。
		 * 2.如果当前对象中的属性字段(不是一个对象)需要映射枚举则用 属性字段_属性值(需要映射的值) 即:cardType_0("虚拟卡") 这样
		 * 3.如果当前对象中的属性字段(是一个对象)需要映射枚举则用 对象属性字段_属性字段_属性值(需要映射的值) 即:card_cardType_0("虚拟卡")
		 * card是个对象,cardType是对象中的一个非对象属性
		 * @param @return 设定文件 返回excel输出流
		 * @return ByteArrayOutputStream
		 * @author yangcan
		 * @date 2016-7-18
		 */
		public  ByteArrayOutputStream exportExcelOutputStreamByReflect(List<? extends Object> objList,String title,String field,Map<String,ExcelFormat> changeAttr) {
			
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				if(CollectionUtils.isEmpty(objList) || StringUtils.isEmpty(title) || StringUtils.isEmpty(field)) {
					return null;
				}
				
				// 第一步，创建一个webbook，对应一个Excel文件  
				wb = new HSSFWorkbook();  
		        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
				sheet = wb.createSheet();  
		        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
				row = sheet.createRow(0);
				
				//写入标题
				String[] titles = title.split(",");
				for (int i = 0; i < titles.length; i++) {
					HSSFCell cell = row.createCell(i); 
					cell.setCellValue(titles[i]);
				}
				
				String[] fields = field.split(",");
				for (int i = 0; i < objList.size(); i++) {
					 //创建行,因为有标题，所以内容从第二行输出
					 row =  sheet.createRow(i+1);
					 //获得当前记录
					 Object obj = objList.get(i);
					 //将当前记录放到当前行中
					 for (int j = 0; j < fields.length; j++) {
						 //通过反射获取属性值
						 try {
							 Object value = getValueByFiledName(obj, fields[j]);
							 if(value == null) {
								 continue;
							 }else {
								 //看该属性值是否需要转化为特定表达值
								 if(changeAttr != null && changeAttr.get(fields[j]) != null) {
									 ExcelFormat format = changeAttr.get(fields[j]);
									 value = format.format(value);
								 }
								 setRowCellValue(value,j);
							 }
						} catch (Exception e) {
							log.error("取该属性的值出错! 属性值:"+fields[j]);
							return null;
						}
					}
				}
				
				wb.write(bos);
			} catch (IOException e) {
				log.error("export excel error!");
				return null;
			}
			return bos;
		}
		
		/**
		 * @Title exportExcelOutputStreamByReflect
		 * @param @param objList 需要导出的list<? extends Object>数据
		 * @param @param title excel标题用逗号隔开 格式如: "xx,yy,zz"
		 * @param @param field excel导出字段用逗号隔开 格式如:"xx,yy,zz"，字段属性必须与标题含义一致  注:如果field是对象的话需要取对象的值则用. 即: 对象.属性
		 * @param @param changeAttr 即需要转换的格式化工具类,如果是需要映射相应的枚举类则应注意以下问题
		 * 1.new ExcelFormatMapper(DiscardEnum.class,"value")  value为 枚举中获取值的属性字段。
		 * 2.如果当前对象中的属性字段(不是一个对象)需要映射枚举则用 属性字段_属性值(需要映射的值) 即:cardType_0("虚拟卡") 这样
		 * 3.如果当前对象中的属性字段(是一个对象)需要映射枚举则用 对象属性字段_属性字段_属性值(需要映射的值) 即:card_cardType_0("虚拟卡")
		 * card是个对象,cardType是对象中的一个非对象属性
		 * @param @return 设定文件 返回excel输出流
		 * @return ByteArrayOutputStream
		 * @author yangcan
		 * @date 2016-7-18
		 */
		public  ByteArrayOutputStream exportExcelOutputStreamByReflect(List<? extends Object> objList,String[] titles,String[] fields) {
			
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				if(CollectionUtils.isEmpty(objList) || titles != null || fields != null) {
					return null;
				}
				
				// 第一步，创建一个webbook，对应一个Excel文件  
				wb = new HSSFWorkbook();  
		        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
				sheet = wb.createSheet();  
		        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
				row = sheet.createRow(0);
				
				//写入标题
				for (int i = 0; i < titles.length; i++) {
					HSSFCell cell = row.createCell(i); 
					cell.setCellValue(titles[i]);
				}
				
				for (int i = 0; i < objList.size(); i++) {
					 //创建行,因为有标题，所以内容从第二行输出
					 row =  sheet.createRow(i+1);
					 //获得当前记录
					 Object obj = objList.get(i);
					 //将当前记录放到当前行中
					 for (int j = 0; j < fields.length; j++) {
						 //通过反射获取属性值
						 try {
							 Object value = getValueByFiledName(obj, fields[j]);
							 if(value == null) {
								 continue;
							 }else {
								 setRowCellValue(value,j);
							 }
						} catch (Exception e) {
							log.error("取该属性的值出错! 属性值:"+fields[j]);
							return null;
						}
					}
				}
				
				wb.write(bos);
			} catch (IOException e) {
				log.error("export excel error!");
				return null;
			}
			return bos;
		}
		
		
		/**
		 * @Title exportExcelOutputStreamByReflect
		 * @param @param objList 需要导出的list<? extends Object>数据
		 * @param @param title excel标题用逗号隔开 格式如: "xx,yy,zz"
		 * @param @param field excel导出字段用逗号隔开 格式如:"xx,yy,zz"，字段属性必须与标题含义一致  注:如果field是对象的话需要取对象的值则用. 即: 对象.属性
		 * @param @param maxLength 每个sheet存储的最大行数, 如果maxLength小于等于0 则默认5000
		 * @param @param changeAttr 即需要转换的格式化工具类,如果是需要映射相应的枚举类则应注意以下问题
		 * 1.new ExcelFormatMapper(DiscardEnum.class,"value")  value为 枚举中获取值的属性字段。
		 * 2.如果当前对象中的属性字段(不是一个对象)需要映射枚举则用 属性字段_属性值(需要映射的值) 即:cardType_0("虚拟卡") 这样
		 * 3.如果当前对象中的属性字段(是一个对象)需要映射枚举则用 对象属性字段_属性字段_属性值(需要映射的值) 即:card_cardType_0("虚拟卡")
		 * card是个对象,cardType是对象中的一个非对象属性
		 * @param @return 设定文件 返回excel输出流
		 * @return ByteArrayOutputStream
		 * @author yangcan
		 * @date 2016-7-18
		 */
		public  ByteArrayOutputStream exportExcelOutputStreamByReflect(List<? extends Object> objList,String title,String field,int maxLength,Map<String,ExcelFormat> changeAttr) {
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				if(CollectionUtils.isEmpty(objList) && StringUtils.isEmpty(title) && StringUtils.isEmpty(field)) {
					return null;
				}
				if(maxLength <=0 ) {
					maxLength = 5000;
				}
				
				// 第一步，创建一个webbook，对应一个Excel文件  
				wb = new HSSFWorkbook();  
		        
				for (int start = 0 , end = 0; end < objList.size(); start += maxLength) {
					if(start + maxLength > objList.size()){
						end = objList.size();
					}else {
						end = start + maxLength;
					}
					
					List<Object> tempList = (List<Object>) objList.subList(start, end);
					// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
					sheet = wb.createSheet();
			        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
					row = sheet.createRow(0);
					
					//写入标题
					String[] titles = title.split(",");
					for (int i = 0; i < titles.length; i++) {
						HSSFCell cell = row.createCell(i); 
						cell.setCellValue(titles[i]);
					}
					
					String[] fields = field.split(",");
					for (int i = 0; i < tempList.size(); i++) {
						 //创建行,因为有标题，所以内容从第二行输出
						 row =  sheet.createRow(i+1);
						 //获得当前记录
						 Object obj = tempList.get(i);
						 //将当前记录放到当前行中
						 for (int j = 0; j < fields.length; j++) {
							 //通过反射获取属性值
							 try {
								 Object value = getValueByFiledName(obj, fields[j]);
								 if(value == null) {
									 continue;
								 }else {
									 //看该属性值是否需要转化为特定表达值
									 if(changeAttr != null && changeAttr.get(fields[j]) != null) {
										 ExcelFormat format = changeAttr.get(fields[j]);
										 value = format.format(value);
									 }
									 setRowCellValue(value,j);
								 }
								 
							} catch (Exception e) {
								log.error("取该属性的值出错! 属性值:"+fields[j]);
								return null;
							}
						}
					}
				}
				wb.write(bos);
			} catch (IOException e) {
				log.error("export excel error!");
				return null;
			}
			return bos;
		}
		
		
		/**
		 * @Title exportExcelOutputStreamByReflect
		 * @param @param objList 需要导出的list<? extends Object>数据
		 * @param @param title 需要导出的title标题的集合
		 * @param @param field 需要导出的field属性的集合
		 * @param @param changeAttr 即需要转换的格式化工具类,如果是需要映射相应的枚举类则应注意以下问题
		 * 1.new ExcelFormatMapper(DiscardEnum.class,"value")  value为 枚举中获取值的属性字段。
		 * 2.如果当前对象中的属性字段(不是一个对象)需要映射枚举则用 属性字段_属性值(需要映射的值) 即:cardType_0("虚拟卡") 这样
		 * 3.如果当前对象中的属性字段(是一个对象)需要映射枚举则用 对象属性字段_属性字段_属性值(需要映射的值) 即:card_cardType_0("虚拟卡")
		 * card是个对象,cardType是对象中的一个非对象属性
		 * @param @return 设定文件
		 * @return ByteArrayOutputStream
		 * @author yangcan
		 * @date 2016-7-18
		 */
		public  ByteArrayOutputStream exportExcelOutputStreamByReflect(List<List<? extends Object>> objList,List<String> title,List<String> field,Map<String,ExcelFormat> changeAttr) {
			
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				if(CollectionUtils.isEmpty(objList) || CollectionUtils.isEmpty(title) || CollectionUtils.isEmpty(field)) {
					return null;
				}
				
				// 第一步，创建一个webbook，对应一个Excel文件  
				wb = new HSSFWorkbook();  
				
				for (int i = 0; i < objList.size(); i++) {
					 // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
					sheet = wb.createSheet();  
			        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
					row = sheet.createRow(0);
					
					//写入标题
					//写入标题
					String[] titles = title.get(i).split(",");
					for (int j = 0; j < titles.length; j++) {
						HSSFCell cell = row.createCell(j); 
						cell.setCellValue(titles[j]);
					}
					
					//获得写入属性
					String[] fields = field.get(i).split(",");
					
					List<Object> curlist = (List<Object>) objList.get(i);
					for (int k = 0; k < curlist.size(); k++) {
						 //创建行,因为有标题，所以内容从第二行输出
						 row =  sheet.createRow(k+1);
						 //获得当前记录
						 Object obj = curlist.get(k);
						 //将当前记录放到当前行中
						 for (int m = 0; m < fields.length; m++) {
							 //通过反射获取属性值
							 try {
								 Object value = getValueByFiledName(obj, fields[m]);
								 if(value == null) {
									 continue;
								 }else {
									//看该属性值是否需要转化为特定表达值
									 if(changeAttr != null && changeAttr.get(fields[m]) != null) {
										 ExcelFormat format = changeAttr.get(fields[m]);
										 value = format.format(value);
									 }
									 setRowCellValue(value,m);
								 }
							} catch (Exception e) {
								log.error("取该属性的值出错! 属性值:"+fields[m]);
								return null;
							}
						}
					}
					
				}
				
				wb.write(bos);
			} catch (IOException e) {
				log.error("export excel error!");
				return null;
			}
			return bos;
		}
		
		
		private  void setRowCellValue(Object obj,int line) {
			
			 HSSFCell cell = row.createCell(line); 
			 if(obj instanceof Integer) {
				 cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				 cell.setCellValue((Integer)obj);
			 }else if(obj instanceof Float) {
				 cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				 cell.setCellValue((Float)obj);
			 }else if(obj instanceof Long) {
				 cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				 cell.setCellValue((Long)obj);
			 }else if(obj instanceof Double) {
				 cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				 cell.setCellValue((Double)obj);
			 }else if(obj instanceof Date) {
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				 cell.setCellValue(new HSSFRichTextString(sdf.format(obj)));
			 }else{
				 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				 cell.setCellValue(obj.toString());
			 }
		}
		
		/**
		 * @Title getValueByFiledName
		 * @param @param obj
		 * @param @param fieldName
		 * @param @return
		 * @param @throws Exception 获得该字段最终所定位的值
		 * @return Object
		 * @author yangcan
		 * @date 2016-7-13
		 */
		private Object getValueByFiledName(Object obj,String fieldName) throws Exception {
			Object value = null;
			String[] fieldNames = fieldName.split("\\.");
			if(fieldNames.length == 1) {
				return getMethodValue(obj, fieldName);
			}else {
				Object javaBean = obj;
				for (int i = 0; i < fieldNames.length ; i++) {
					
					if(i < fieldNames.length-1) {
						//还是对象school.banji.username
						javaBean = getMethodValue(javaBean, fieldNames[i]);
					}else {
						//说明是最后一个对象的属性
						value = getMethodValue(javaBean, fieldNames[i]);
					}
					if(null == javaBean){
						log.error("没有找到该属性"+fieldNames[i]);
						System.out.println("没有找到该属性"+fieldNames[i]);
						break;
					}
				}
			}
			return value;
		}
		
		/**
		 * @Title getMethodValue
		 * @param @param obj
		 * @param @param fieldName
		 * @param @return
		 * @param @throws Exception  获得该字段fieldName对应的get方法的值
		 * @return Object
		 * @author yangcan
		 * @date 2016-7-13
		 */
		private Object getMethodValue(Object obj,String fieldName) throws Exception {
			Object value = null;
			PropertyDescriptor proDesc = new PropertyDescriptor(fieldName , obj.getClass());
			value = proDesc.getReadMethod().invoke(obj);
			return value;
		}
		
		public static void main(String args[]){
//			Student s = new Student();
//			s.setAge(10);
//			s.setUsername("admin");
//			s.setPassword("123");
//			s.setBirthday(new Date());
//			s.setFlag(true);
//
//			School school = new School();
//			school.setUsername("皇家理工");
//			school.setSchoolAge(50);
//			s.setSchool(school);
//			
//			Banji banji = new Banji();
//			banji.setBanjiAge(2010);
//			banji.setBanjiName("计算机科学与技术");
//			banji.setUsername("123");
//			school.setBanji(banji);
//
//			Student s2 = new Student();
//			s2.setAge(20);
//			s2.setUsername("admin2");
//			s2.setPassword("456");
//			s2.setBirthday(new Date());
//
//			School school2 = new School();
//			school2.setUsername("皇家理工2");
//			school2.setSchoolAge(50);
//			s2.setSchool(school2);
//			
//			Banji banji2 = new Banji();
//			banji2.setBanjiAge(2014);
//			banji2.setBanjiName("计算机科学与技术2");
//			banji2.setUsername("456");
//			school2.setBanji(banji2);
//			
//			
//			List<Student> stuList = new ArrayList<Student>();
//			stuList.add(s);
//			stuList.add(s2);
//			
//			Map<String,Object> mapObject = new HashMap<String,Object>();
//			mapObject.put("10", "小孩");
//			mapObject.put("20", "青年");
//			mapObject.put("50", "中年");
//			
//			Map<String,ExcelFormat> fmap = new HashMap<String,ExcelFormat>();
//			fmap.put("age", new ExcelMapperFormat(mapObject));
//			fmap.put("school.schoolAge", new ExcelStringFormat());
//			String title = "姓名,姓名2,密码,生日,年龄,学校姓名,学校年限,班级名称,班级年限,flag";
//			String field = "username,username,password,birthday,age,school.username,school.schoolAge,school.banji.banjiName,school.banji.banjiAge,flag";
//			ByteArrayOutputStream outputStream = ExcelUtilHSSF.getInstance().exportExcelOutputStreamByReflect(stuList, title, field,1,fmap);
//			try {
//				FileOutputStream out = new FileOutputStream("D:/student.xls");
//				outputStream.writeTo(out);
//				outputStream.close();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//			List<String> titleList = new ArrayList<String>();
//			List<String> fieldList = new ArrayList<String>();
//			
//			List<Student> stu = new ArrayList<Student>();
//			stu.add(s);
//			titleList.add("姓名,密码,年龄");
//			fieldList.add("username,password,age");
//			
//			List<School> sch = new ArrayList<School>();
//			sch.add(school);
//			titleList.add("学校名称,学校年限");
//			fieldList.add("username,schoolAge");
//			
//			List<Banji> ban = new ArrayList<Banji>();
//			ban.add(banji);
//			titleList.add("班级名称,班级年限");
//			fieldList.add("banjiName,banjiAge");
//			
//			List<List<? extends Object>> threeList = new ArrayList<List<? extends Object>>();
//			threeList.add(stu);
//			threeList.add(sch);
//			threeList.add(ban);
//			
//			outputStream = ExcelUtilHSSF.getInstance().exportExcelOutputStreamByReflect(threeList, titleList, fieldList, fmap);
//			try {
//				FileOutputStream out = new FileOutputStream("D:/zonghe.xls");
//				outputStream.writeTo(out);
//				outputStream.close();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
		}
}
