/*
 * Project Name: spring-boot
 * File Name: IBaseBiz.java
 * Copyright: Copyright(C) 1985-2016 ZKTeco Inc. All rights reserved.
 */
package com.zz.core.biz;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.zz.core.exception.BaseException;

public interface IBaseBiz<T> {

	public List<T> findAll();

	public T findOne(String id);

	public List<T> findAll(Iterable<String> ids);

	public Page<T> findAll(Pageable pageable);

	public Page<T> findAll(int pageNo, int pageSize);

	public Page<T> findAll(int pageNo, int pageSize, Map<String, Object> conditions);
	
	public Page<T> findAll(int pageNo, int pageSize, List<String> orders);

	public Page<T> findAll(int pageNo, int pageSize, Map<String, Object> conditions, List<String> orders);

	public List<T> findAll(Sort sort);

	public void delete(String id) throws BaseException;

	public void delete(T entity);

	public void delete(Iterable<? extends T> entities);
	
	public void deleteByIds(String[] ids);
	
	public void deleteAll();

	public T save(T entity);

	public List<T> save(Iterable<T> entities);

	public T saveAndFlush(T entity);

	public void flush();

	public boolean exists(String id);

}
