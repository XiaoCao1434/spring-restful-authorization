package com.beini.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.beini.authorization.entity.MenuInfo;

public interface MenuRepository extends JpaRepository<MenuInfo, Integer>,JpaSpecificationExecutor<MenuInfo>{

}
