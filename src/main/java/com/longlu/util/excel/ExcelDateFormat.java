package com.longlu.util.excel;

import java.text.SimpleDateFormat;

public class ExcelDateFormat implements ExcelFormat{
	
	private String pattern;

	public Object format(Object obj) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(obj);
	}
	
	public ExcelDateFormat(String pattern) {
		this.pattern = pattern;
	}
	

}
