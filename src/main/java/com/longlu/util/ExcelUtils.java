package com.longlu.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/***
 * 
 * 利用java反射机制获得属性值进行poi导出
 * **/
public class ExcelUtils<T> {
	/**
	 * wb
	 */
	Workbook wb = null;
	/**
	 * sheet
	 */
	public Sheet sheet = null;

	/**
	 * 获取所读取excel模板的对象
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public Sheet getSheet(String filePath) {
		try {

			File fi = new File(filePath);
			if (!fi.exists()) {
				System.out.println("模板文件:" + filePath + "不存在!");
				return null;
			}
			InputStream ins = new FileInputStream(fi);
			wb = WorkbookFactory.create(ins);
			ins.close();
			// 得到excel工作表对象
			sheet = wb.getSheetAt(0);
		} catch (FileNotFoundException e) {
			System.out.println("文件模版不存在!");
		} catch (IOException e) {
			System.out.println("输入输出流异常!");
		} catch (InvalidFormatException e) {
			System.out.println("输入输出流异常!");
			e.printStackTrace();
		}
		return sheet;
	}

	/**
	 * 完成导出输出流
	 * 
	 * @param desXlsPath
	 *            导出文件路劲
	 */
	public ByteArrayOutputStream exportToOutputStream() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
		} catch (IOException e) {
			System.out.println("输入输出流异常!");
		}
		return bos;
	}

	/****
	 * poi导出excel方法 全表所有字段导出 杨灿 2015-8-12
	 * 
	 * @param rowIndex
	 *            表示从第几行开始导入，0表示第一行
	 * @param list
	 *            需要导入的list数据泛型
	 * @param tempPath
	 *            模版文件的绝对地址
	 * @return 返回输出数据流
	 * @throws Exception
	 * @throws NoSuchMethodException
	 * **/

	public ByteArrayOutputStream exportExcelOutputStream(int rowIndex,List<T> list, String tempPath) throws Exception {
		
		Row row;
		this.getSheet(tempPath);

		// 开始遍历list
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			// 创建行
			row = this.sheet.createRow(rowIndex++);

			// 获得下一个的实体类
			T t = it.next();
			// 获得实体类的所有属性
			Field[] fields = t.getClass().getDeclaredFields();
			
			// 遍历所有属性
			for (int i = 0; i < fields.length; i++) {
				String fieldname = fields[i].getName();
				String getMethodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
				// 通过get属性的方法名获得属性值
				Method method = t.getClass().getMethod(getMethodName,new Class[] {});
				// 获得属性的值
				Object obj = method.invoke(t);
				// 根据属性值的类型插入excel中
				if(obj == null) {
					//row.createCell(i).setCellValue("");
				}else if (obj instanceof Integer) {
					row.createCell(i).setCellValue((Integer) obj);
				} else if (obj instanceof Float) {
					row.createCell(i).setCellValue((Float) obj);
				} else if (obj instanceof Double) {
					row.createCell(i).setCellValue((Double) obj);
				} else if (obj instanceof Long) {
					row.createCell(i).setCellValue((Long) obj);
				} else if(obj instanceof BigDecimal) {
					row.createCell(i).setCellValue((Double) obj);
				}else if (obj instanceof Date) {
					Date date = (Date) obj;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					row.createCell(i).setCellValue(sdf.format(date));
				} else {
					row.createCell(i).setCellValue(obj.toString());
				}
			}
		}

		ByteArrayOutputStream bos = this.exportToOutputStream();
		return bos;
	}
	
	
	public ByteArrayOutputStream exportExcelOutputStream(List<T> list, String title) throws Exception {
		
		int rowIndex = 0;
		// 第一步，创建一个webbook，对应一个Excel文件  
		XSSFWorkbook wb = new XSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		XSSFSheet sheet = wb.createSheet();  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		Row row = sheet.createRow(0);
		
		//写入标题
		String[] titles = title.split(",");
		for (int i = 0; i < titles.length; i++) {
			XSSFCell cell = (XSSFCell) row.createCell(i); 
			cell.setCellValue(titles[i]);
		}
		
		// 开始遍历list
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			// 创建行
			row = this.sheet.createRow(rowIndex++);

			// 获得下一个的实体类
			T t = it.next();
			// 获得实体类的所有属性
			Field[] fields = t.getClass().getDeclaredFields();
			
			// 遍历所有属性
			for (int i = 0; i < fields.length; i++) {
				String fieldname = fields[i].getName();
				String getMethodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
				// 通过get属性的方法名获得属性值
				Method method = t.getClass().getMethod(getMethodName,new Class[] {});
				// 获得属性的值
				Object obj = method.invoke(t);
				// 根据属性值的类型插入excel中
				if(obj == null) {
					//row.createCell(i).setCellValue("");
				}else if (obj instanceof Integer) {
					row.createCell(i).setCellValue((Integer) obj);
				} else if (obj instanceof Float) {
					row.createCell(i).setCellValue((Float) obj);
				} else if (obj instanceof Double) {
					row.createCell(i).setCellValue((Double) obj);
				} else if (obj instanceof Long) {
					row.createCell(i).setCellValue((Long) obj);
				} else if(obj instanceof BigDecimal) {
					row.createCell(i).setCellValue((Double) obj);
				}else if (obj instanceof Date) {
					Date date = (Date) obj;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					row.createCell(i).setCellValue(sdf.format(date));
				} else {
					row.createCell(i).setCellValue(obj.toString());
				}
			}
		}

		ByteArrayOutputStream bos = this.exportToOutputStream();
		return bos;
	}
	
	
	/****
	 *  poi导出excel方法 有选择的导出表中的字段 ，仅筛选一个字段杨灿 2015-8-12
	 * 
	 * @param rowIndex
	 *            表示从第几行开始导入，0表示第一行
	 * @param list
	 *            需要导入的list数据泛型
	 * @param tempPath
	 *            模版文件的绝对地址
	 * @param s
	 *            筛选的实体类的属性名称
	 * @return 返回输出数据流
	 * @throws Exception
	 * @throws NoSuchMethodException
	 * **/

	public ByteArrayOutputStream exportExcelOutputStream(int rowIndex,List<T> list, String tempPath,String s) throws Exception {
		if(s == null) {
			//调用导出全字段的方法
			return exportExcelOutputStream(rowIndex,list,tempPath);
		}
		Row row;
		this.getSheet(tempPath);

		// 开始遍历list
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			// 创建行
			row = this.sheet.createRow(rowIndex++);

			// 获得下一个的实体类
			T t = it.next();
			// 获得实体类的所有属性
			Field[] fields = t.getClass().getDeclaredFields();
			
			//判断表中是否有id属性的标识位
			int isId = 0;
			// 遍历所有属性
			for (int i = 0; i < fields.length; i++) {
				String fieldname = fields[i].getName();
				
				//要筛选的字段
				if(s.equals(fieldname)) {
					continue;
				}
				String getMethodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
				// 通过get属性的方法名获得属性值
				Method method = t.getClass().getMethod(getMethodName,new Class[] {});
				// 获得属性的值
				Object obj = method.invoke(t);

				// 根据属性值的类型插入excel中
				if(obj == null) {
					//row.createCell(i).setCellValue("");
				}else if (obj instanceof Integer) {
					row.createCell(isId).setCellValue((Integer) obj);
				} else if (obj instanceof Float) {
					row.createCell(isId).setCellValue((Float) obj);
				} else if (obj instanceof Double) {
					row.createCell(isId).setCellValue((Double) obj);
				} else if (obj instanceof Long) {
					row.createCell(isId).setCellValue((Long) obj);
				} else if (obj instanceof Date) {
					Date date = (Date) obj;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					row.createCell(isId).setCellValue(sdf.format(date));
				} else {
					row.createCell(isId).setCellValue(obj.toString());
				}
				
				isId++;
			}
		}

		ByteArrayOutputStream bos = this.exportToOutputStream();
		return bos;
	}
	
	/****
	 *  poi导出excel方法 有选择的导出表中的字段 ，仅筛选一个字段杨灿 2015-8-12
	 * 
	 * @param rowIndex
	 *            表示从第几行开始导入，0表示第一行
	 * @param list
	 *            需要导入的list数据泛型
	 * @param tempPath
	 *            模版文件的绝对地址
	 * @param str
	 *            筛选的实体类的属性名称,String数组
	 * @return 返回输出数据流
	 * @throws Exception
	 * @throws NoSuchMethodException
	 * **/

	public ByteArrayOutputStream exportExcelOutputStream(int rowIndex,List<T> list, String tempPath,String[] str) throws Exception {
		//判断筛选的字段数组是否为空
		if(str == null) {
			//调用导出全字段的方法
			return exportExcelOutputStream(rowIndex,list,tempPath);
		}
		Row row;
		this.getSheet(tempPath);

		// 开始遍历list
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			// 创建行
			row = this.sheet.createRow(rowIndex++);
			// 获得下一个的实体类
			T t = it.next();
			// 获得实体类的所有属性
			Field[] fields = t.getClass().getDeclaredFields();
			//判断表中是否有id属性的标识位
			int isId = 0;
			// 遍历所有属性
			for (int i = 0; i < fields.length; i++) {
				String fieldname = fields[i].getName();
				
				//要筛选的字段
				boolean flag = false;
				for (int j = 0; j < str.length; j++) {
					if(str[j].equals(fieldname)) {
						flag = true;
						break;
					}
				}
				if(flag) {
					continue;
				}
				String getMethodName = "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
				// 通过get属性的方法名获得属性值
				Method method = t.getClass().getMethod(getMethodName,new Class[] {});
				// 获得属性的值
				Object obj = method.invoke(t);

				// 根据属性值的类型插入excel中
				if(obj == null) {
					//row.createCell(i).setCellValue("");
				}else if (obj instanceof Integer) {
					row.createCell(isId).setCellValue((Integer) obj);
				} else if (obj instanceof Float) {
					row.createCell(isId).setCellValue((Float) obj);
				} else if (obj instanceof Double) {
					row.createCell(isId).setCellValue((Double) obj);
				} else if (obj instanceof Long) {
					row.createCell(isId).setCellValue((Long) obj);
				} else if (obj instanceof Date) {
					Date date = (Date) obj;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					row.createCell(isId).setCellValue(sdf.format(date));
				} else {
					row.createCell(isId).setCellValue(obj.toString());
				}
				
				isId++;
			}
		}

		ByteArrayOutputStream bos = this.exportToOutputStream();
		return bos;
	}
	
	

	public static void main(String args[]){

	}
}
