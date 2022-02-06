package com.saurabh.operation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.saurabh.constant.DBConstant;
import com.saurabh.constant.QueryList;
import com.saurabh.entity.User;
import com.saurabh.entity.UserDetail;
import com.saurabh.exception.UserNotFoundException;
import com.saurabh.service.UserDetailService;





public class DBOperation {
	Connection con;
     public DBOperation() throws ClassNotFoundException, SQLException {
 		 con= DriverManager.getConnection(DBConstant.URL, DBConstant.USER,DBConstant.PASSWORD);
		// TODO Auto-generated constructor stub
	}
	
     @Autowired
      UserDetailService userDetailService;
    
    
    
    public UserDetail readUserDetailObject(String name) throws SQLException
    {
    	con.setAutoCommit(false);
		Statement stmt=con.createStatement();
		String sql=QueryList.READ_USERDETAILOBJECT+"'"+name+"'";
		ResultSet rs=stmt.executeQuery(sql);
		UserDetail userDetail = new UserDetail();
		if(rs.next())
		{
			userDetail.setId(rs.getInt("id"));
			userDetail.setMobile(rs.getString("mobile"));
			userDetail.setAddress(rs.getString("address"));
		}

		return userDetail;
    }
    
    
    
    public Map<Integer,Object> readUserDetail(Map<Integer,Object> userDetailMap) throws SQLException
    {
    	con.setAutoCommit(false);
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(QueryList.READ_USERDETAIL);
		while(rs.next())
		{
			UserDetail userDetail = new UserDetail();
			userDetail.setId(rs.getInt("id"));
			userDetail.setMobile(rs.getString("mobile"));
			userDetail.setName(rs.getString("name"));
			userDetail.setAddress(rs.getString("address"));
			userDetailMap.put(userDetail.getId(), userDetail);
		}
		return userDetailMap;
		
    }
    
    
    
    public Map<Integer,Object> readUserDetail(int id) throws SQLException
    {
    	con.setAutoCommit(false);
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(QueryList.SELECT_USERDETAIL_ID+id+"'");
		Map<Integer,Object>  userDetailMap=new HashMap<Integer, Object>();
		while(rs.next())
		{
			UserDetail userDetail = new UserDetail();
			userDetail.setId(rs.getInt("id"));
			userDetail.setMobile(rs.getString("mobile"));
			userDetail.setName(rs.getString("name"));
			userDetail.setAddress(rs.getString("address"));
			userDetailMap.put(userDetail.getId(), userDetail);
		}
		return userDetailMap;
    }
    
    
    
    
    public Map<Integer,Object> readUserDetail(String name) throws SQLException
    {
    	con.setAutoCommit(false);
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery(QueryList.SELECT_USERDETAIL_NAME+name+"'");
		Map<Integer,Object>  userDetailMap=new HashMap<Integer, Object>();
		while(rs.next())
		{
			UserDetail userDetail = new UserDetail();
			userDetail.setId(rs.getInt("id"));
			userDetail.setMobile(rs.getString("mobile"));
			userDetail.setName(rs.getString("name"));
			userDetail.setAddress(rs.getString("address"));
			userDetailMap.put(userDetail.getId(), userDetail);
		}
		return userDetailMap;
    }
        
    
    
    public int getStatus(String name)
    {
    	int result=-2;
    	try {
			con.setAutoCommit(false);
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(QueryList.CHECK_STATUS+"'"+name+"'");
			if(rs.next())
			{
				int status=rs.getInt("status");
				if(status==1)
				{
				    result =1;
				}else if(status==0)
				{
					result=0;
				}else if(status==2)
				{
					result=2;
				}
			}else
			{
				result=-1;
				throw new UserNotFoundException();
			}
		} catch (SQLException | UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    
    public List<User> getNewUnapprovedUser()
    {
    	List<User> listUser=new ArrayList<>();
    	try {
			con.setAutoCommit(false);
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(QueryList.GET_NEW_UNAPPROVED_USER);
			while(rs.next())
			{
				User user=new User();
				user.setUserName(rs.getString("username"));
				user.setId(rs.getInt("id"));
				user.setRoles(rs.getString("roles"));
				listUser.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return listUser;
    }
}

