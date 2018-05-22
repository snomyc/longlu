//package com.longlu.util;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServlet;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.longlu.service.impl.KdtApiServiceImpl;
//
//import java.io.*;
//import java.net.URL;
//import java.net.URLDecoder;
//import java.util.Properties;
//
//
///**
// * @author Administrator
// * 用于读取项目中的配置文件
// */
//public class PropertiesUtil {
//
//	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
//	
//    private String filename;
//    private Properties p;
//    private FileInputStream in;
//    private FileOutputStream out;
//
//    public PropertiesUtil() {
//        super();
//        URL url = this.getClass().getClassLoader().getResource("config.properties");
//        //解码,地址空格变成%20
//        this.filename = URLDecoder.decode(url.getFile());
//        File file = new File(this.filename);
//        try {
//            in = new FileInputStream(file);
//            p = new Properties();
//            //解决中文乱码问题
//            p.load(new InputStreamReader(in,"UTF-8"));
//            in.close();
//        } catch (FileNotFoundException e) {
//        	logger.error("配置文件config.properties找不到！");
//        	logger.error(e.getMessage(),e);
//        } catch (IOException e) {
//        	logger.error("读取配置文件config.properties错误！");
//        	logger.error(e.getMessage(),e);
//        }
//    }
//
//    public PropertiesUtil(String filename) {
//        this.filename = filename;
//        File file = new File(filename);
//        try {
//            in = new FileInputStream(file);
//            p = new Properties();
//            p.load(new InputStreamReader(in,"UTF-8"));
//            in.close();
//        } catch (FileNotFoundException e) {
//        	logger.error("配置文件config.properties找不到！");
//        	logger.error(e.getMessage(),e);
//        } catch (IOException e) {
//        	logger.error("读取配置文件config.properties错误！");
//        	logger.error(e.getMessage(),e);
//        }
//    }
//    public static String getConfigFile(HttpServlet hs) {
//        return getConfigFile(hs, "config.properties");
//    }
//
//    private static String getConfigFile(HttpServlet hs, String configFileName) {
//        String configFile = "";
//        ServletContext sc = hs.getServletContext();
//        configFile = sc.getRealPath("/" + configFileName);
//        if (configFile == null || configFile.equals("")) {
//            configFile = "/" + configFileName;
//        }
//
//        return configFile;
//    }
//
//    public void list() {
//        p.list(System.out);
//    }
//
//    public String getValue(String itemName) {
//        return p.getProperty(itemName);
//    }
//
//    public String getValue(String itemName, String defaultValue) {
//        return p.getProperty(itemName, defaultValue);
//    }
//
//    public void setValue(String itemName, String value) {
//        p.setProperty(itemName, value);
//    }
//
//    public void saveFile(String filename, String description) throws Exception {
//        try {
//            File f = new File(filename);
//            out = new FileOutputStream(f);
//            p.store(out, description);
//            out.close();
//        } catch (IOException ex) {
//            throw new Exception("无法保存指定的配置文件:" + filename);
//        }
//    }
//
//    public void saveFile(String filename) throws Exception{
//        saveFile(filename,"");
//    }
//
//    /**
//     * 保存文件
//     * @throws Exception
//     */
//    public void saveFile() throws Exception{
//        if(filename.length()==0)
//            throw new Exception("需指定保存的配置文件名");
//            saveFile(filename);
//    }
//
//    /**
//     * 删除值
//     * @param value
//     */
//    public void deleteValue(String value){
//        p.remove(value);
//    }
//
//}
