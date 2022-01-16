package com.saurabh.operation;

import java.util.Random;
import java.util.Set;


public class GenerateUniqueId 
{
   public int generateId(Set<Object> retrieveId)
   {
	   Random random = new Random();
	   int i =  random.nextInt(100);
	   if(retrieveId.contains(i))
	   {
		   generateId(retrieveId);
	   }  
	   return i;
}
}
