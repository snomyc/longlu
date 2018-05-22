//package com.longlu.util;
//import java.awt.Color;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import javax.imageio.ImageIO;
//import org.apache.log4j.Logger;
//
//import com.swetake.util.Qrcode;
//
///**
// * 二维码生成器
// * @author xbird
// *
// */
//public class TwoDimensionCode {
//	
//	/**
//	 * 私有构造函数
//	 */
//	private TwoDimensionCode() {}
//	/**
//	 * 日志工具类
//	 */
//	private static Logger logger = Logger.getLogger(TwoDimensionCode.class);
//    /**
//     * 生成二维码(QRCode)图片
//     * @param content 内容
//     * @param imgPath 存储路径
//     * @param width 图片宽度
//     * @param height 图片高度
//     */
//    public static void encoderQRCode(String content, String imgPath, int width, int height) {
//        try {
//            Qrcode qrcodeHandler = new Qrcode();
//            qrcodeHandler.setQrcodeErrorCorrect('M');
//            qrcodeHandler.setQrcodeEncodeMode('B');
//            qrcodeHandler.setQrcodeVersion(10);
//            byte[] contentBytes = content.getBytes("utf-8");
//            BufferedImage bufImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
//            Graphics2D gs = bufImg.createGraphics();
//            gs.setBackground(Color.WHITE);
//            gs.clearRect(0, 0, height, height);
//            // 设定图像颜色 > BLACK
//            gs.setColor(Color.BLACK);
//            // 设置偏移量 不设置可能导致解析出错
//            int pixoff = 2;
//            // 输出内容 > 二维码
//            if (contentBytes.length > 0 && contentBytes.length < 500) {
//	                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
//	                for (int i = 0; i < codeOut.length; i++) {
//	                    for (int j = 0; j < codeOut.length; j++) {
//	                        if (codeOut[j][i]) {
//	                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
//	                        }
//	                    }
//	                }
//            } else {
//            	logger.error("QRCode content bytes length = " + contentBytes.length + " not in [0, 500 ]. ");
//            }
//            gs.dispose();
//            bufImg.flush();
//            File imgFile = new File(imgPath);
//            // 生成二维码QRCode图片
//            ImageIO.write(bufImg, "png", imgFile);
//        } catch (Exception e) {
//            logger.error(e);
//        }
//    }
//    
//    public static void main(String[] args) {
//		
//    	String imgPath = "F:/龙路数据网二维码.png";
//		String encoderContent = "http://192.168.1.128:8080/easychannel/index.jsp";
//		//TwoDimensionCode handler = new TwoDimensionCode();
//		TwoDimensionCode.encoderQRCode(encoderContent, imgPath, 175, 175);
//    	
//	}
//    
//    
///*	public static void main(String[] args) {
//		String imgPath = "F:/Michael_QRCode1.png";
//		String encoderContent = "http://www.baidu.com";
//		TwoDimensionCodeLogo handler = new TwoDimensionCodeLogo();
//		handler.encoderQRCode(encoderContent, imgPath, "png", "F:/logo.png");
//		String imgPath = "F:/Michael_QRCode1.png";
//		String encoderContent = "http://192.168.0.80:8080/queryProjectExpand.do?caseId=2de6616d73ef43dfabc79a24be35470d&projectName=黄石市区城市雨水循环处理黄石市区城市雨水循环处理黄石黄雨水循环处理黄石黄处理";
//		TwoDimensionCode.encoderQRCode(encoderContent, imgPath, 175, 175);
//		try {
//		     String content = "http://192.168.0.80:8080/queryProjectExpand.do?caseId=2de6616d73ef43dfabc79a24be35470d&projectName=黄石市区城市雨水循环处理黄石市区城市雨水";
//		     String path = "f:/testImage";
//		     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//		     File file1 = new File(path,"餐巾纸5.jpg");
//		     Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
//		     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//		     hints.put(EncodeHintType.MARGIN, 1);
//		     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 110, 110, hints);
//		     MatrixImageWriterUtils.writeToFile(bitMatrix, "jpg", file1);
//		 } catch (Exception e) {
//		 }
//		
//	}*/
//    
//}