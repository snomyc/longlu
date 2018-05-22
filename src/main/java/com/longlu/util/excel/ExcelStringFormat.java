package com.longlu.util.excel;

public class ExcelStringFormat implements ExcelFormat{

	public Object format(Object obj) {
		return obj.toString();
	}

}
