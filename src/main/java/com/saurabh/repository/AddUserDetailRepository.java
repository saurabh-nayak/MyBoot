package com.saurabh.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.saurabh.entity.UserDetail;


@Repository
@Service
public interface AddUserDetailRepository extends CrudRepository<UserDetail,Integer> {

}
