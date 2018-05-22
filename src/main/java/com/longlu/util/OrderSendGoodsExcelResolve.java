package com.longlu.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import com.longlu.pojo.AliSendGoods;
import com.longlu.service.AliSendGoodsService;

/**
 * @author yangcan
 * 解析发货订单excel并生成新的发货订单
 */
public class OrderSendGoodsExcelResolve {

	private Workbook wb;
	private Sheet sheet;
	private Row row;
	
	/**
	 * 解析并生成新的excel文件流
	 * @return
	 * @author yangcan
	 */
	public void excelResolve(InputStream ins,AliSendGoodsService aliSendGoodsService) {
		try {
			// 第一步，创建一个webbook，获取传入的excel文件流信息
			wb = WorkbookFactory.create(ins);
			// 第二步，获取excel sheet信息  
			sheet = wb.getSheetAt(0);
			
			//总行数
			int sumRow = sheet.getPhysicalNumberOfRows();
//			row = sheet.getRow(11);
//			System.out.print(row.getCell(0).getStringCellValue()+"\t");
//			System.out.print(row.getCell(18).getStringCellValue()+getGoodsName(11, sumRow, sheet)+"\t");
			
			//从第二行开始读取
			for (int i = 1; i < sumRow; i++) {
				row = sheet.getRow(i);
				
				//如果该列没有订单编号则继续往下读取
				if(row == null || row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
					continue;
				}
				
//				//获取第1列订单编号
//				System.out.print(row.getCell(0).getStringCellValue()+"\t");
//				//获取第14列收货人姓名
//				System.out.print(row.getCell(13).getStringCellValue()+"\t");
//				//获取第15列收货地址
//				System.out.print(row.getCell(14).getStringCellValue()+"\t");
//				//获取第18列联系手机
//				System.out.print(row.getCell(17).getStringCellValue()+"\t");
//				//获取第19列商品名称 //如果下一列的订单编号为空则表示是组合订单
//				System.out.print(row.getCell(18).getStringCellValue()+getGoodsName(i, sumRow, sheet)+"\t");
//				//获取第29列物流公司运单号
//				System.out.println(row.getCell(28).getStringCellValue());
				
				AliSendGoods record = new AliSendGoods();
				//订单编号
				record.setOrderCode(row.getCell(0).getStringCellValue());
				//收货人姓名
				record.setReceiverName(row.getCell(13).getStringCellValue());
				//收货地址
				record.setReceiverAddress(row.getCell(14).getStringCellValue());
				//联系手机
				String mobile = row.getCell(17).getStringCellValue();
				if(StringUtils.isBlank(mobile)) {
					//如果手机为空那么取电话信息
					mobile = row.getCell(16).getStringCellValue();
				}
				record.setReceiverMobile(mobile);
				//商品名称
				record.setGoodsName(row.getCell(18).getStringCellValue()+getGoodsName(i, sumRow, sheet));
				
				//物流信息
				String expressCell = row.getCell(28).getStringCellValue();
				if(StringUtils.isNotBlank(expressCell)) {
					String[] express = expressCell.split(":");
					//物流公司
					record.setExpressCompany(express[0]);
					//物流单号
					record.setExpressNumber(express[1]);
					try{
						aliSendGoodsService.insert(record);
					}catch (Exception e) {
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getGoodsName(int rowNum,int sumRow,Sheet sheet) {
		Row row = sheet.getRow(rowNum+1);
		if((rowNum+1) < sumRow && (row == null || row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue()))) {
			String goodsName = "【●】"+sheet.getRow(rowNum+1).getCell(18).getStringCellValue();
			return goodsName+getGoodsName(rowNum+1, sumRow, sheet);
		}
		return "";
	}
	
	/**
	 * 解析并生成新的excel文件流
	 * @return
	 * @author yangcan
	 */
	public ByteArrayOutputStream excelResolve_bak(InputStream ins) {
		try {
			// 第一步，创建一个webbook，获取传入的excel文件流信息
			wb = WorkbookFactory.create(ins);
			// 第二步，获取excel sheet信息  
			sheet = wb.getSheetAt(0);
			//获取第二行
			row = sheet.getRow(1);
			//删除第二行的第二列单元格内容
			//row.removeCell(row.getCell(1));
			//总行数
			System.out.println(sheet.getPhysicalNumberOfRows());
			
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			wb.write(bos);
			return bos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
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
	
	
	public static void main(String[] args) throws Exception {
//		File fi = new File("F:/阿里巴巴订单.xls");
//		InputStream ins = new FileInputStream(fi);
//		new OrderSendGoodsExcelResolve().excelResolve(ins);
		
//		FileOutputStream out = new FileOutputStream("D:/"+fi.getName());
//		outputStream.writeTo(out);
//		outputStream.close();
	}
}
