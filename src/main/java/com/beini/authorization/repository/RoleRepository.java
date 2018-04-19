package com.beini.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.beini.authorization.entity.RoleInfo;

public interface RoleRepository extends JpaRepository<RoleInfo, Integer>, JpaSpecificationExecutor<RoleInfo> {

}
