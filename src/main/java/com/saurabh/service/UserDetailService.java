package com.saurabh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saurabh.entity.UserDetail;
import com.saurabh.repository.AddUserDetailRepository;

@Service
public class UserDetailService {
	
	@Autowired
	AddUserDetailRepository addUserDetailRepository;
	
	public UserDetail add(UserDetail userDetail)
	{
		UserDetail savedUserDetail= addUserDetailRepository.save(userDetail);
		return savedUserDetail;
	}

	
    public Iterable<UserDetail> search()
    {
    	Iterable<UserDetail> allUserDetails=addUserDetailRepository.findAll();
    	return allUserDetails;
    }
    
    public void delete(UserDetail userDetail)
    {
    	addUserDetailRepository.delete(userDetail);
    }

}
