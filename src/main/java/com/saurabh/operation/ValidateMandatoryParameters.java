package com.saurabh.operation;

import java.util.Map;
import java.util.Set;

import com.saurabh.constant.ErrorConstant;
import com.saurabh.constant.MandatoryParameters;
import com.saurabh.pojo.ResponseData;




public class ValidateMandatoryParameters 
{
   public ResponseData validateParameters(Map<String, String> requestMap)
   {
	   ResponseData response=null;
	   Set<String> setRequest= requestMap.keySet();
		if(setRequest.contains(MandatoryParameters.USERNAME.getParameterName()) && !requestMap.get(MandatoryParameters.USERNAME.getParameterName()).isEmpty() && requestMap.get(MandatoryParameters.USERNAME.getParameterName())!=null &&
				setRequest.contains(MandatoryParameters.PASSWORD.getParameterName()) && !requestMap.get(MandatoryParameters.PASSWORD.getParameterName()).isEmpty() && requestMap.get(MandatoryParameters.PASSWORD.getParameterName())!=null &&
				setRequest.contains(MandatoryParameters.NAME.getParameterName()) && !requestMap.get(MandatoryParameters.NAME.getParameterName()).isEmpty() && requestMap.get(MandatoryParameters.NAME.getParameterName())!=null &&
				setRequest.contains(MandatoryParameters.ADDRESS.getParameterName()) && !requestMap.get(MandatoryParameters.ADDRESS.getParameterName()).isEmpty() && requestMap.get(MandatoryParameters.ADDRESS.getParameterName())!=null &&
				setRequest.contains(MandatoryParameters.MOBILE.getParameterName()) && !requestMap.get(MandatoryParameters.MOBILE.getParameterName()).isEmpty() && requestMap.get(MandatoryParameters.MOBILE.getParameterName()) !=null
				)
		
		{
			if(requestMap.get(MandatoryParameters.PASSWORD.getParameterName()).length()<4)
			{
				response =new ResponseData(ErrorConstant.PASSWORD_MINIMUM.getErrorMessage(),ErrorConstant.PASSWORD_MINIMUM.getErrorCode());
			}
		}else {
			 response =new ResponseData(ErrorConstant.MANDATORY_PARAMETERS_MISSING.getErrorMessage(),ErrorConstant.MANDATORY_PARAMETERS_MISSING.getErrorCode());
			 return response;	
		 }
								
		 response=new ResponseData(ErrorConstant.MANDATORY_PARAMETERS_OK.getErrorMessage(), ErrorConstant.MANDATORY_PARAMETERS_OK.getErrorCode());
   
		 return response;
   }
}
