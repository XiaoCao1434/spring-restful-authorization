package com.beini.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.beini.authorization.entity.UserRoleInfo;

public interface UserRoleRepository extends JpaRepository<UserRoleInfo, Integer>, JpaSpecificationExecutor<UserRoleInfo> {

}
