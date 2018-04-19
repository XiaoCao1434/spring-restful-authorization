package com.beini.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.beini.authorization.entity.AuthorityInfo;

public interface AuthorityRepository extends JpaRepository<AuthorityInfo, String>, JpaSpecificationExecutor<AuthorityInfo> {

}
