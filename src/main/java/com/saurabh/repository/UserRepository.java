package com.saurabh.repository;



import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saurabh.entity.User;

@Repository
@Service
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query(value="select id , pass , roles , userName , status from user where userName = ?1 and status<> 2",nativeQuery = true )
	Optional<User> findByUserName(String userName);
	
	@Query(value="select id from user", nativeQuery = true)
	public Set<Object> retrieveAllId();
	
	@Modifying
	@Transactional
	@Query(value="UPDATE user set pass= ?1 where username=?2", nativeQuery = true)
	public int updatePassword(String password, String username);

}
