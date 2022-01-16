package com.saurabh.constant;

public enum MandatoryParameters 
{
	USERNAME("username"),
	PASSWORD("password"),
	ADDRESS("address"),
	NAME("name"),
	MOBILE("mobile"),
    ROLES("roles"),
    STATUS("status");
	String parameterName;
	private MandatoryParameters(String parameterName)
	{
		this.parameterName=parameterName;
	}
	
	public String getParameterName()
	{
		return this.parameterName;
	}
    
}
