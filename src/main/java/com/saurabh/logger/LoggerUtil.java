package com.saurabh.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggerUtil {
	public static Logger logger = LogManager.getLogger(LoggerUtil.class);
	
	public void debug(String debug)
	{
		logger.debug(debug);
	}
	
	public void info(String info)
	{
		logger.info(info);
	}
	
	public void error(String error)
	{
		logger.error(error);
	}

}
