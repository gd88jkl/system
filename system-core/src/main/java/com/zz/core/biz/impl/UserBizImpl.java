/*
 * Project Name: spring-boot
 * File Name: UserBizImpl.java
 * Copyright: Copyright(C) 1985-2016 ZKTeco Inc. All rights reserved.
 */
package com.zz.core.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zz.core.biz.IUserBiz;
import com.zz.core.entity.User;
import com.zz.core.persistence.BaseJpaRepository;
import com.zz.core.repository.UserRepository;

@Service
public class UserBizImpl extends BaseBizImpl<User, BaseJpaRepository<User, String>> implements IUserBiz {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> findByName(String name) {
		return userRepository.findByName(name);
	}

}
