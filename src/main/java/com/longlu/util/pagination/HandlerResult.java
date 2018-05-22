package com.longlu.util.pagination;


import org.apache.log4j.Logger;



public class HandlerResult implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5746865980348298097L;

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(HandlerResult.class);
	
	ResultState resultState;
	
	protected Object resultObj;
	
	protected Pagination pagination;
	
	protected ClientCallbackInfo clientCallbackInfo;
	
	public ResultState getResultState() {
		return resultState;
	}

	public void setResultState(ResultState resultState) {
		this.resultState = resultState;
	}

	public HandlerResult(){}

	public ClientCallbackInfo getClientCallbackInfo()
	{
		return clientCallbackInfo;
	}

	public void setClientCallbackInfo(ClientCallbackInfo clientCallbackInfo)
	{
		this.clientCallbackInfo = clientCallbackInfo;
	}


	public Pagination getPagination() {
		return pagination;
	}

	public void setPage(Pagination pagination) {
		this.pagination = pagination;
	}

	public Object getResultObj() {
		return resultObj;
	}

	public void setResultObj(Object resultObj) {
		this.resultObj = resultObj;
	}


}
