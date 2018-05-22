package com.longlu.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author yangcan
 * 读取文件并修改文件
 */
public class FileTest {

	
	public static void customBufferStreamCopy(File source, File target) {  
	    InputStream fis = null;  
	    OutputStream fos = null;  
	    try {  
	        fis = new FileInputStream(source);  
	        fos = new FileOutputStream(target);  
	        byte[] buf = new byte[4096];  
	        int i;  
	        while ((i = fis.read(buf)) != -1) {  
	            fos.write(buf, 0, i);  
	        } 
	        
			fis.close();
			fos.close();
	    }  
	    catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	    	
	    }  
	}
	
	
	public static void editFileData(File file) throws Exception {
		OutputStream fos = new FileOutputStream(file,true);
		Writer out = new BufferedWriter(new OutputStreamWriter(fos));
		String text = "我是新加入的\r\n";
		out.write(text);
		out.flush();
		out.close();
		fos.close();
	}
	
	public static void main(String[] args) {
//		File file = new File("F:/ceshi.txt");
		File file = new File("F:/常见问题汇总.docx");
		try {
			FileTest.editFileData(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
