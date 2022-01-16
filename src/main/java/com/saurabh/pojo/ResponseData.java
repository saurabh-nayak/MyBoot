package com.saurabh.pojo;

public class ResponseData 
{
    private String jwt;
    
	public ResponseData(String jwt,int responseCode,String responseMessage)
	{
	     this.jwt=jwt;
	     this.responseCode=responseCode;
	     this.responseMessage=responseMessage;
	}
	public ResponseData(String responseMessage, int responseCode)
	{
		this.responseMessage=responseMessage;
		this.responseCode=responseCode;
	}
	ResponseData()
	{
		
	}
	
	public String getJwt()
	{
		return jwt;
	}
	
	int responseCode;
	String responseMessage;
	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}
	@Override
	public String toString() {
		return "responseCode=" + responseCode + ", responseMessage=" + responseMessage
				;
	}
	

}
