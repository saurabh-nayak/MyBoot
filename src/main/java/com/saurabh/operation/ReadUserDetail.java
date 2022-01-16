package com.saurabh.operation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ReadUserDetail {
	public static Map<Integer,Object> userDetailMap=new HashMap<>();
	public void readUserDetail()
	{	
	    try {
			userDetailMap=new DBOperation().readUserDetail(userDetailMap);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	}
}
	public static Map<Integer,Object> getUserDetailMap()
	{
		return userDetailMap;
	}
}
