package com.beini.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.beini.authorization.entity.RoleMenusInfo;

public interface RoleMenusRepository extends JpaRepository<RoleMenusInfo, Integer>, JpaSpecificationExecutor<RoleMenusInfo> {

}
