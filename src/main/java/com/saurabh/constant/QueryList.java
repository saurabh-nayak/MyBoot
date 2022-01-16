package com.saurabh.constant;

public class QueryList 
{
	public static String SELECT_STUDENT          =  "select * from student";
	public static String INSERT_STUDENT          =  "insert into student values(?,?,?,?)";
	public static String SELECT_EMPLOYEE         =  "select * from employee";
	public static String INSERT_EMPLOYEE         =  "insert into employee values(?,?,?,?)";
	public static String GET_EMPLOYEE            =  "select * from employee where id= ";
	public static String GET_STUDENT             =  "select * from student where id= ";
	public static String INSERT_USER             =  "insert into user(userName,pass,id) values(?,?,?)";
	public static String INSERT_USERDETAIL       =  "INSERT INTO userDetail(ID,NAME,ADDRESS,MOBILE) VALUES(?,?,?,?)";
	public static String READ_USERDETAIL         =  "SELECT * FROM userDetail";
	public static String READ_USERDETAILOBJECT   =  "select id,address,mobile from userDetail where name = ";
	public static String SELECT_USERDETAIL_ID    =  "select * from userDetail where id= ?";
	public static String SELECT_USERDETAIL_NAME  =  "select * from userDetail where name='";
	public static String UPDATE_PASSWORD         =  "UPDATE user set pass= ? where username=?";
	public static String CHECK_STATUS            =  "SELECT status FROM user WHERE username= ";
	public static String GET_NEW_UNAPPROVED_USER =  "SELECT * FROM user where status = 0";
}

