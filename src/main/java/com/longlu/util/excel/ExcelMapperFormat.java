package com.longlu.util.excel;
import java.util.Map;

/**
 * @author yangcan
 *  需要映射的键值对工具类 即需要将key转换成value的工具类
 */
public class ExcelMapperFormat implements ExcelFormat{

	private Map<String,Object> map;
	public Object format(Object key) {
		key = String.valueOf(key);
		if(map != null && map.get(key) != null) {
			return map.get(key);
		}
		return "wrong Value";
	}
	public ExcelMapperFormat(Map<String,Object> map) {
		this.map = map;
	}
	
}
