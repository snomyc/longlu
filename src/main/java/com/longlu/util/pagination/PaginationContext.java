package com.longlu.util.pagination;


/**
 * 分页上下文
 * @author LiCheng、ZhangQin
 *
 */
public class PaginationContext extends Pagination{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5105260736495219051L;
	
	/**
	 * 解决多线程程序的并发问题
	 */
	private static ThreadLocal<PaginationContext> context = new ThreadLocal<PaginationContext>();
	
	/**
	 * 获取PaginationContext对象
	 * @return PaginationContext对象
	 */
	public static PaginationContext getContext()
	{
		PaginationContext pc = context.get();
		if(pc==null)
		{
			pc = new PaginationContext();
			context.set(pc);
		}
		return pc;
	}
	
	/**
	 * 移除PaginationContext对象
	 */
	public static void removeContext()
	{
		context.remove();
	}

}
