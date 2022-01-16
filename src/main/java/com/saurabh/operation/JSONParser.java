package com.saurabh.operation;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JSONParser {
	
	public Map<String,String> parse(String request)
	{
		ObjectMapper mapper=new ObjectMapper();
		Map<String, String> requestMap=null;
		try {
			requestMap = mapper.readValue(request, Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestMap;
	}

}
