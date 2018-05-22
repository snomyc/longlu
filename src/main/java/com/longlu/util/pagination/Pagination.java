package com.longlu.util.pagination;

import java.io.Serializable;

/**
 * EasyUI Pagination 模型
 * @author ZhangQin
 *
 */
public class Pagination implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否启用分页
	 */
	private boolean enable=true;
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * 初始分页
	 * @param total 总记录数
	 * @param rows 总行数
	 * @param page 当前页数
	 */
	public void init(int total, int rows) {
		this.rows = rows;
		this.total = total;

		if ((total % rows) == 0) {
			countPage = total / rows;
		} else {
			countPage = total / rows + 1;
		}

	}
	
	/**
	 * 当前页数
	 */
	private int page = 1;
	
	private int countPage; //总页数
	/**
	 * 行数
	 */
	private int rows = 5;
	/**
	 * 总行数
	 */
	private int total;
	/**
	 * 查询的开始行
	 */
	private int firstResult;

	public int getFirstResult() {
		firstResult = (this.getPage() - 1) * rows;
		return firstResult;
	}
	
	public int getPage() {
		if(page <= 0) {
			page = 1;
		}
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		if(rows<=0){
			rows = 10;
		}
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCountPage() {
		return countPage;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}
	
}
