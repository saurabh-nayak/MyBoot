package com.saurabh.controllers;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.saurabh.constant.ErrorConstant;
import com.saurabh.entity.User;
import com.saurabh.entity.UserDetail;
import com.saurabh.logger.LoggerUtil;
import com.saurabh.operation.DBOperation;
import com.saurabh.operation.GenerateUniqueId;
import com.saurabh.operation.JSONParser;
import com.saurabh.operation.JWTGenerator;
import com.saurabh.operation.ReadUserDetail;
import com.saurabh.operation.ValidateMandatoryParameters;
import com.saurabh.pojo.MyUserDetails;
import com.saurabh.pojo.ResponseData;
import com.saurabh.repository.AddUserDetailRepository;
import com.saurabh.repository.UserRepository;
import com.saurabh.service.MyUserDetailsService;
import com.saurabh.service.UserDetailService;
import com.saurabh.service.UserService;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;



@RestController
@CrossOrigin(origins="*", allowedHeaders="*", maxAge = 3600,allowCredentials="true",exposedHeaders="**")
public class MyControllers {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JWTGenerator jwtGenerator;
	
	@Autowired
	MyUserDetailsService userDetailsService;
		
	@Autowired
	JSONParser jsonParser;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddUserDetailRepository addUserDetailRepository;
	
	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	LoggerUtil logger;
	
	@RequestMapping(value="/admin",
			method = {RequestMethod.POST})
	public Map<Integer,Object> admin()
	{
	return new ReadUserDetail().getUserDetailMap();
	}
	
	
	
	@RequestMapping(value="/getUser",
			method = {RequestMethod.GET})
	public UserDetail admin(@RequestHeader(value="name") String name) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException, SQLException
	{

			UserDetail userDetail=new DBOperation().readUserDetail(name);
		
		    return userDetail;
		
	}
	
	@RequestMapping(value="/user",
			method = {RequestMethod.POST})
	public String user()
	{
		logger.debug("in /user");
		logger.info("info");
		return "hello user";
	}
	
	
	
	
	@Consumes("application/json")
	@Produces("application/json")
	@RequestMapping(value="/signup",method = RequestMethod.POST,produces = "application/json")
	public ResponseEntity<?> signUp(@RequestBody String request) 
	{
		ResponseData response; 
		Map<String, String> requestMap=null;
		requestMap = jsonParser.parse(request);
		User user= new User();
		UserDetail userDetail = new UserDetail();		

		 response=new ValidateMandatoryParameters().validateParameters(requestMap);
					if(response.getResponseCode()==100 )
					{
						Set<Object> retrieveId=userRepository.retrieveAllId();
						int id=new GenerateUniqueId().generateId(retrieveId);
						user.setId(id);
						user.setPass(new BCryptPasswordEncoder().encode(requestMap.get("password")));
						user.setUserName(requestMap.get("username"));
						userDetail.setId(id);userDetail.setAddress(requestMap.get("address"));
						userDetail.setMobile(requestMap.get("mobile"));
						userDetail.setName(requestMap.get("name"));
						User savedUser = userService.add(user);
						UserDetail savedUserDetail=null;
						try 
						{
						   savedUserDetail= userDetailService.add(userDetail);
						}catch(Exception e)
						{
							userService.delete(savedUser);
						}
		if(!savedUser.equals(null) && !savedUserDetail.equals(null))
		{
			response =new ResponseData(ErrorConstant.SUCCESSFULL_SIGNUP.getErrorMessage(),ErrorConstant.SUCCESSFULL_SIGNUP.getErrorCode());	
		}
		else {
			response =new ResponseData("error occured during signup",-201);
		}
					}
		return ResponseEntity.ok(response);	
	}

	


	
    @RequestMapping(value="/authenticate",method = RequestMethod.POST, consumes = "application/json" , produces = "application/json")
    @CrossOrigin(origins="*", maxAge=3600, allowedHeaders="*")
    public ResponseEntity<?> authenticateUser(@RequestBody String request) 
    {
    	logger.debug("request for authenticate came");
    	logger.info("info message");
    	String jwt=null;
    	Map<String, String> requestMap=null;
		requestMap = jsonParser.parse(request);
		Set<String> setRequest= requestMap.keySet();
		ResponseEntity<ResponseData> response=null;
		if(setRequest.contains("username") && setRequest.contains("password") && setRequest.size()==2)
		{
			
		    	try {
		    	Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("username"),requestMap.get("password")));
		    	UserDetails userDetails=userDetailsService.loadUserByUsername(requestMap.get("username"));
		    	if(authentication.isAuthenticated())
		    	{
		    		
			    	jwt=jwtGenerator.generateToken(userDetails);
			    	response = ResponseEntity.ok(new ResponseData(jwt,200,"SUCCESS"));
		    	}
		    	else {
		    		response = ResponseEntity.ok(new ResponseData(ErrorConstant.USER_STATUS_NOT_ACTIVE.getErrorMessage(), ErrorConstant.USER_STATUS_NOT_ACTIVE.getErrorCode()));
		    	}
		    	}catch(BadCredentialsException e)
		    	{
		    		response=new ResponseEntity(new ResponseData(ErrorConstant.BAD_CREDENTIAS.getErrorMessage(),ErrorConstant.BAD_CREDENTIAS.getErrorCode()),
		    				HttpStatus.UNAUTHORIZED);
		    	}
		    	
		    	
			}else {
    	response = ResponseEntity.ok(new ResponseData(jwt,ErrorConstant.MANDATORY_PARAMETERS_MISSING.getErrorCode(),ErrorConstant.MANDATORY_PARAMETERS_MISSING.getErrorMessage()));	
    }
		return response;
    }
    
    
    @RequestMapping(value="/changePassword",method = RequestMethod.POST, consumes = "application/json" , produces = "application/json")
    public ResponseEntity<?> changePassword(@RequestBody String request) throws ClassNotFoundException, SQLException
    {
		ResponseData response=null;
//		boolean result;
		Map<String, String> requestMap=null;
		requestMap = new JSONParser().parse(request);
		
		Set<String> setRequest= requestMap.keySet();
		MyUserDetails userDetails = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String principal=userDetails.getUsername();
		if(setRequest.contains("oldPassword") && setRequest.contains("newPassword") && !requestMap.get("newPassword").equals(requestMap.get("oldPassword")))
		{
			String newPassword=new BCryptPasswordEncoder().encode(requestMap.get("newPassword"));
			int result=userRepository.updatePassword(newPassword,principal);		
			
		
			if(result==1) {
			response=new ResponseData(ErrorConstant.PASSWORD_CHANGED_SUCCESSFULL.getErrorMessage(),ErrorConstant.PASSWORD_CHANGED_SUCCESSFULL.getErrorCode());
			}else {
				response=new ResponseData(ErrorConstant.ERROR_CHANGE_PASSWORD.getErrorMessage(),ErrorConstant.ERROR_CHANGE_PASSWORD.getErrorCode());
			}
		
		}
		else {
			response=new ResponseData(ErrorConstant.MANDATORY_MISSING_OR_PASSWORD_ARE_EQUAL.getErrorMessage(),ErrorConstant.MANDATORY_MISSING_OR_PASSWORD_ARE_EQUAL.getErrorCode());
		}
		
		return ResponseEntity.ok(response);
		
    }
    
    @RequestMapping(value="/getUnapprovedUsers",method = RequestMethod.GET , produces = "application/json")
    public ResponseEntity<?> getUnapprovedUsers()
    {
    	System.out.println("In getUnapprovedUsers");
    	List<User> listUser=new ArrayList<>();
    	ResponseEntity<?> response=null;
    	try {
			listUser=new DBOperation().getNewUnapprovedUser();
			if(listUser.size()==0)
			{
				response = ResponseEntity.ok(new ResponseData(ErrorConstant.NO_UNAPPROVED_USER.getErrorMessage(),ErrorConstant.NO_UNAPPROVED_USER.getErrorCode()));
			}
			else {
				response = ResponseEntity.ok(listUser);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response;
    }
    
    
    
    @RequestMapping(value="/*",method = RequestMethod.POST, consumes = "application/json" , produces = "application/json")
    public ResponseEntity<?> notFound()
    {
  
    	ResponseData response=new ResponseData(ErrorConstant.REQUEST_MAPPING_NOT_FOUND.getErrorMessage(),ErrorConstant.REQUEST_MAPPING_NOT_FOUND.getErrorCode());
    	return ResponseEntity.ok(response);
    }
    
    
	@RequestMapping(value="/getWether", consumes = "application/json", method= RequestMethod.POST)
	public ResponseEntity<?> getWeatherData(@RequestBody String request) {
		
		
		Map<String, String> requestMap=null;
		Map<String, String> responseMap=null;
			
		requestMap = jsonParser.parse(request);
		String responseBody=null;
		HttpResponse<String> response=null;
		if(requestMap.get("location")!=null && !requestMap.get("location").isEmpty())
		{
			
			response = Unirest.get("https://community-open-weather-map.p.rapidapi.com/weather?callback=test&id=2172797&units=%2522metric%2522%20or%20%2522imperial%2522&mode=xml%252C%20html&q="+requestMap.get("location"))
				.header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
				.header("x-rapidapi-key", "3b72fbd0c2mshe3df2f23f03a462p19f203jsndc7fa4a51e4b")
				.asString();
			
			responseBody=response.getBody();
			
			if(responseBody.contains("test(") && responseBody.contains(")"))
			{
				
			   StringBuffer buffer = new StringBuffer(responseBody);
		       buffer.deleteCharAt(buffer.lastIndexOf(")"));
		       buffer.delete(0,buffer.indexOf("(")+1);
		       responseMap=new JSONParser().parse(buffer.toString());
		       
			}else {
				responseMap=new JSONParser().parse(responseBody.toString());	
			}
					
		}else {
			ResponseEntity.ok(new ResponseData(ErrorConstant.PLEASE_ENTER_LOCATION.getErrorMessage(),ErrorConstant.PLEASE_ENTER_LOCATION.getErrorCode()));
		}
				

	return ResponseEntity.ok(responseMap);
	} 
	
	@RequestMapping(value="/getCorona", consumes = "application/json", method= RequestMethod.POST)
	public ResponseEntity<?> getCorona(@RequestBody String request)
	{
		Map<String, String> requestMap=null;
		requestMap = jsonParser.parse(request);
		String responseBody=null;
		Map<String, String> responseMap=null;
		String subURl=null;
		HttpResponse<String> response=null;
		if(requestMap.get("start_date")!=null && requestMap.get("end_date")!=null && !requestMap.get("start_date").isEmpty() && !requestMap.get("end_date").isEmpty())
		{
			String start_date=requestMap.get("start_date");
			String end_date=requestMap.get("end_date");
			subURl="start_date="+start_date+"&end_date="+end_date;
			response = Unirest.get("https://covid-19-india-data-by-zt.p.rapidapi.com/GetIndiaHistoricalDataBetweenDates?"+subURl)
					.header("x-rapidapi-host", "covid-19-india-data-by-zt.p.rapidapi.com")
					.header("x-rapidapi-key", "3b72fbd0c2mshe3df2f23f03a462p19f203jsndc7fa4a51e4b")
					.asString();
			responseMap=jsonParser.parse(response.getBody());
		}else if(requestMap.get("country")!=null)
		{
			response = Unirest.get("https://covid-193.p.rapidapi.com/statistics?country="+requestMap.get("country"))
					.header("x-rapidapi-host", "covid-193.p.rapidapi.com")
					.header("x-rapidapi-key", "3b72fbd0c2mshe3df2f23f03a462p19f203jsndc7fa4a51e4b")
					.asString();
			responseMap=jsonParser.parse(response.getBody());
		}
			else {
			return ResponseEntity.ok(new ResponseData(ErrorConstant.MANDATORY_PARAMETERS_MISSING.getErrorMessage(), ErrorConstant.MANDATORY_PARAMETERS_MISSING.getErrorCode()));
		}
            return ResponseEntity.ok(responseMap);
	}
	
	
	@RequestMapping(value="/addUser")
	public void addUser()
	{
		String userName="MS.Dhoni";
		String pass="1234";
		String name="MS.Dhoni";
		int id=17;
		String address="abcdefgh";
		String mobile="1234567890";
		String encodedPass=new BCryptPasswordEncoder().encode(pass);
		User user= new User();
		UserDetail userDetail= new UserDetail();
		user.setPass(encodedPass);user.setUserName(userName);
		userDetail.setId(id);userDetail.setAddress(address);
		userDetail.setName(name);userDetail.setMobile(mobile);
		userService.add(user);
		System.out.println("In addUser");
		userDetailService.add(userDetail);
		
	}
}

