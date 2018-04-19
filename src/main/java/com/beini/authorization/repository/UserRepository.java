package com.beini.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.beini.authorization.model.User;

/**
 * User类的CRUD操作
 * 
 * @see com.beini.authorization.model.User
 * @author lb_chen
 */
public interface UserRepository extends JpaRepository<User, String> {

	User findByUsername(String username);

	@Query("from User where id=:id")
	User findOne(@Param("id") String id);

}
