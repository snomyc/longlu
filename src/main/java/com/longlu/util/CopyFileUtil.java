package com.longlu.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

public class CopyFileUtil {

	
	public static void nioTransferCopy(File source, File target) {  
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
	    } finally {  
	    }  
	}
	
	
	public static void customBufferBufferedStreamCopy(File source, File target) {  
	    InputStream fis = null;  
	    OutputStream fos = null;  
	    try {  
	        fis = new BufferedInputStream(new FileInputStream(source));  
	        fos = new BufferedOutputStream(new FileOutputStream(target));  
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
	
	public static void main(String[] args) throws Exception {
		File file1 = new File("F:/123.xlsx");
		String ss = "E:/dddddddd/";
		File ab = new File(ss);
		if(!ab.exists()) {
			//创建目录
			ab.mkdir();
		}
		
		File file2 = new File(ss+file1.getName());
		CopyFileUtil.nioTransferCopy(file1,file2);
	}
	
}
