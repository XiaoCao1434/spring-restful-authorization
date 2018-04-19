package com.beini.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.beini.authorization.entity.UserInfo;

/**
 * User类的CRUD操作
 * 
 * @see com.beini.authorization.model.User
 * @author lb_chen
 */
public interface UserRepository extends JpaRepository<UserInfo, String> {

	UserInfo findByUsername(String username);

	@Query("from UserInfo where id=:id")
	UserInfo findOne(@Param("id") String id);

}
