/*
 * Project Name: spring-boot
 * File Name: IUserBiz.java
 * Copyright: Copyright(C) 1985-2016 ZKTeco Inc. All rights reserved.
 */
package com.zz.core.biz;

import java.util.List;

import com.zz.core.biz.IBaseBiz;
import com.zz.core.entity.User;

public interface IUserBiz extends IBaseBiz<User>{

	public List<User> findByName(String name);

}