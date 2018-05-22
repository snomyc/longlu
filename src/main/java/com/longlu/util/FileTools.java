package com.longlu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
/**
 * 删除文件夹及文件操作
 * **/
public class FileTools {
	
	/**
	* 删除文件夹及文件夹下的所有文件
	**/
	public static boolean deletePaperFiles(String filepath){
		
		//创建一个文件对象
		File file = new File(filepath);
		
		//判断是文件还是文件夹
		if(!file.isDirectory()) {
			//是文件则删除
			//System.out.println("删除文件"+file.getName()+"成功!");
			file.delete();
			return true;
		}else {
			//是目录，获得该目录下的所有文件列表
			File[] files = file.listFiles();
			for(int i=0; i<files.length; i++) {
				
				File delfile = new File(filepath+"\\"+files[i].getName());
				//System.out.println(delfile.getPath());
				
				if(!delfile.isDirectory()) {
					//是文件
					//System.out.println("删除文件"+delfile.getName()+"成功!");
					delfile.delete();
				}else {
					
					deleteFiles(delfile.getPath());
					//删除目录
					//System.out.println("删除目录为:"+delfile.getPath());
					delfile.delete();
				}
				
			}
			
		}
		
		//当所有文件都删除完毕，然后删除当前文件夹 如果不要file.delete()则是删除文件夹下的所有文件
		//System.out.println("删除总目录:"+file.getPath()+"成功!");
		file.delete();
		return true;
	}
	
	/**
	* 删除文件夹下的所有文件不包括文件夹
	**/
	public static boolean deleteFiles(String filepath){
		//创建一个文件对象
		File file = new File(filepath);
		//判断是文件还是文件夹
		if(!file.isDirectory()) {
			System.out.println("删除文件"+file.getName()+"成功!");
			//是文件则删除
			file.delete();
			return true;
		}else {
			//是目录，获得该目录下的所有文件列表
			File[] files = file.listFiles();
			for(int i=0; i<files.length; i++) {
				File delfile = new File(filepath+"\\"+files[i].getName());
				if(!delfile.isDirectory()) {
					//是文件 删除
					System.out.println("删除文件"+delfile.getName()+"成功!");
					delfile.delete();
				}else {
					
					deleteFiles(delfile.getPath());
					//删除目录
					System.out.println("删除目录为:"+delfile.getPath());
					delfile.delete();
				}
			}
		}
		return true;
	}
	
	
	/***
	 * 复制文件(单个文件)且不能复制文件夹
	 * start:需要复制的文件地址
	 * end:需要复制到哪个位置的路径结尾需带上/
	 * **/
	public static boolean CopyFile(String start, String end) {  
		File source = new File(start);
		File target = new File(end+source.getName());
		if(!source.exists() || !target.exists()) {
			return false;
		}
		if(source.isDirectory()) {
			return false;
		}
		
	    FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null;  
	    try {  
	        inStream = new FileInputStream(source);  
	        outStream = new FileOutputStream(target);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        in.transferTo(0, in.size(), out);  
	        
			inStream.close();
			in.close();
	    	outStream.close();
	    	out.close();
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
	    return true;
	}
	
	/***
	 * 剪切文件(单个文件)
	 * start:需要剪切的文件地址
	 * end:需要剪切到哪个位置的路径结尾需带上/
	 * **/
	public static boolean CutFile(String start, String end) {  
		File source = new File(start);
		File target = new File(end+source.getName());
		if(!source.exists() || !target.exists()) {
			return false;
		}
		if(source.isDirectory()) {
			return false;
		}
	    FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null;  
	    try {  
	        inStream = new FileInputStream(source);  
	        outStream = new FileOutputStream(target);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        in.transferTo(0, in.size(), out);  
	        
	        inStream.close();
			in.close();
	    	outStream.close();
	    	out.close();
	    	
	    	//删除原文件
	    	source.delete();
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
	    return true;
	}
	
	public static String getFormatSize(double size) {  
        double kiloByte = size/1024;  
        if(kiloByte < 1) {  
            return size + "Byte(s)";  
        }  
          
        double megaByte = kiloByte/1024;  
        if(megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
        }  
          
        double gigaByte = megaByte/1024;  
        if(gigaByte < 1) {  
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
        }  
          
        double teraBytes = gigaByte/1024;  
        if(teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
    }
	
	/** 
	* @Title: getZnFileType 
	* @Description: TODO 
	* @param @param suffix
	* @param @return 返回文件类型
	* @return String    返回类型 
	* @authoer yangcan
	* @throws 
	*/ 
	public static String getZnFileType(String suffix) {
		
		//将suffix后缀名转换为小写
		suffix = suffix.toLowerCase();
		if(suffix.endsWith(".exe")) {
			return "EXE";
		}else if(suffix.endsWith(".bmp") || suffix.endsWith(".gif") || suffix.endsWith(".jpg") || suffix.endsWith(".png")
				|| suffix.endsWith(".pic") || suffix.endsWith(".tif")) {
			return "图片";
		}else if(suffix.endsWith(".doc") || suffix.endsWith(".docx")) {
			return "WORD";
		}else if(suffix.endsWith(".xls") || suffix.endsWith(".xlsx")) {
			return "EXCEL";
		}else if(suffix.endsWith(".zip") || suffix.endsWith(".rar")) {
			return "压缩包";
		}else if(suffix.endsWith(".pdf")) {
			return "PDF";
		}else if(suffix.endsWith(".txt")) {
			return "记事本";
		}else if(suffix.endsWith(".ppt") || suffix.endsWith(".pptx")) {
			return "PPT";
		}else if(suffix.endsWith(".mp3")) {
			return "音乐";
		}else if(suffix.endsWith(".mp4") || suffix.endsWith(".avi") || suffix.endsWith(".rm") || suffix.endsWith(".mkv")) {
			return "视频";
		}
		return "OTHER";
	}
	
	/**
	 * 读取文件
	 * 
	 * @param path
	 *            String
	 * @return String
	 */
	public static String readFileContext(String path) {
		String string = "";
		String charsetName = "UTF-8";
		File file = new File(path);
		try {
			if (file.isFile() && file.exists()) {
				InputStreamReader insReader = new InputStreamReader(
						new FileInputStream(file), charsetName);
				BufferedReader bufReader = new BufferedReader(insReader);
				String line = new String();
				while ((line = bufReader.readLine()) != null) {
					string = string + line;
				}
				bufReader.close();
				insReader.close();
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return string;
	}
	
	
	public static void main(String[] args) {
		String filepath = "E:/ext-6.0.0-gpl/ext-6.0.0/";
		deleteFiles(filepath);
		//boolean flag = FileUtils.CutFile("F:/123/yc.jpg", "E:/");
		//System.out.println(flag);
		//System.out.println(getFormatSize(10245));
	}
}
