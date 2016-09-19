/*
 * Project Name: spring-boot
 * File Name: UserRepository.java
 * Copyright: Copyright(C) 1985-2016 ZKTeco Inc. All rights reserved.
 */
package com.zz.core.repository;

import java.util.List;

import com.zz.core.entity.User;
import com.zz.core.persistence.BaseJpaRepository;

public interface UserRepository extends BaseJpaRepository<User,String> {

	public List<User> findByName(String name);
	
}
