package com.saurabh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saurabh.entity.User;
import com.saurabh.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User add(User user)
	{
		User savedUser=userRepository.save(user);
		return savedUser;
	}
	
    public Iterable<User> search()
    {
    	Iterable<User> allUsers=userRepository.findAll();
    	return allUsers;
    }
    
    public void delete(User user)
    {
    	userRepository.delete(user);
    }

}
