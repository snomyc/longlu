package com.longlu.util.excel;

import java.text.DecimalFormat;

public class ExcelNumberFormat implements ExcelFormat{
	private String pattern;

	public Object format(Object obj) {
		DecimalFormat df = new DecimalFormat(pattern);
		return Double.valueOf(df.format(obj));
	}
	
	public ExcelNumberFormat(String pattern) {
		this.pattern = pattern;
	}
	
}
