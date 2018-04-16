package com.beini.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.beini.domain.User;

/**
 * User类的CRUD操作
 * 
 * @see com.beini.domain.User
 * @author lb_chen
 */
public interface UserRepository extends CrudRepository<User, String> {

	User findByUsername(String username);

	Page<User> findAll(Pageable pageable);

	@Query("from User where id=:id")
	User findOne(@Param("id") String id);

}
