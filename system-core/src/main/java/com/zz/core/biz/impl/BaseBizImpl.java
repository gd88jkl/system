/*
 * Project Name: spring-boot
 * File Name: BaseBizImpl.java
 * Copyright: Copyright(C) 1985-2016 ZKTeco Inc. All rights reserved.
 */
package com.zz.core.biz.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.zz.core.biz.IBaseBiz;
import com.zz.core.exception.BaseException;
import com.zz.core.exception.ExceptionCodes;
import com.zz.core.persistence.BaseJpaRepository;

public class BaseBizImpl<T, Repository extends BaseJpaRepository<T, String>> implements IBaseBiz<T> {

	@Autowired
	protected Repository repository;

	@Override
	public T findOne(String id) {
		return repository.findOne(id);
	}

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public List<T> findAll(Iterable<String> ids) {
		return repository.findAll(ids);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Page<T> findAll(int pageNo, int pageSize) {
		Pageable pageable = new PageRequest(pageNo, pageSize);
		return repository.findAll(pageable);
	}

	@Override
	public Page<T> findAll(int pageNo, int pageSize, Map<String, Object> conditions) {
		Pageable pageable = new PageRequest(pageNo, pageSize);
		if (conditions != null && !conditions.isEmpty()) {
			return this.findAll(pageable, conditions, new ArrayList<String>());
		}
		return repository.findAll(pageable);
	}

	@Override
	public Page<T> findAll(int pageNo, int pageSize, List<String> orders) {
		Pageable pageable = new PageRequest(pageNo, pageSize);
		if (orders != null && !orders.isEmpty()) {
			return this.findAll(pageable, new HashMap<String, Object>(), orders);
		}
		return repository.findAll(pageable);
	}

	@Override
	public Page<T> findAll(int pageNo, int pageSize, Map<String, Object> conditions, List<String> orders) {
		Pageable pageable = new PageRequest(pageNo, pageSize);
		if (conditions != null && !conditions.isEmpty() && orders != null && !orders.isEmpty()) {
			return this.findAll(pageable, conditions, orders);
		}
		if (conditions != null && !conditions.isEmpty()) {
			return this.findAll(pageable, conditions, new ArrayList<String>());
		}
		if (orders != null && !orders.isEmpty()) {
			return this.findAll(pageable, new HashMap<String, Object>(), orders);
		}
		return repository.findAll(pageable);
	}
	
	@Override
	public List<T> findAll(Sort sort) {
		return repository.findAll(sort);
	}
	
	@Override
	public void delete(String id) throws BaseException {
		try {
			repository.delete(id);
		} catch (Exception e) {
			throw new BaseException(BaseException.setMessage(ExceptionCodes.RECODE_NOT_EXIST,new String[]{id}));
		}
	}
	
	@Override
	public void delete(T entity) {
		repository.delete(entity);
	}
	
	@Override
	public void delete(Iterable<? extends T> entities) {
		repository.delete(entities);
	}
	
	@Override
	public void deleteByIds(String[] ids) {
		if(ids != null && ids.length > 0) {
			for (String id : ids) {
				repository.delete(id);
			}
		}
	}
	
	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
	
	@Override
	public T save(T entity) {
		return repository.save(entity);
	}
	
	@Override
	public List<T> save(Iterable<T> entities) {
		return repository.save(entities);
	}
	
	@Override
	public T saveAndFlush(T entity) {
		return repository.saveAndFlush(entity);
	}
	
	@Override
	public void flush() {
		repository.flush();
	}
	
	@Override
	public boolean exists(String id) {
		return repository.exists(id);
	}

	private Page<T> findAll(Pageable pageable, final Map<String, Object> conditions, final List<String> orders) {
		Page<T> page = repository.findAll(new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> restrictions = new ArrayList<Predicate>();
				String key = "";
				for (Map.Entry<String, Object> entry : conditions.entrySet()) {
					if (entry.getKey().endsWith("between")) {
						key = entry.getKey().substring(0, entry.getKey().length() - 7);
						List<?> list = (List<?>) entry.getValue();
						if (list != null && !list.isEmpty()) {
							if (list.get(0).getClass().isInstance(new Date())) {
								restrictions.add(cb.between(
										root.get(root.getModel().getSingularAttribute(key, Date.class)),
										cb.literal((Date) list.get(0)), cb.literal((Date) list.get(1))));
							}
							if (list.get(0).getClass().isInstance(new Integer(0))) {
								restrictions.add(cb.between(
										root.get(root.getModel().getSingularAttribute(key, Integer.class)),
										cb.literal((Integer) list.get(0)), cb.literal((Integer) list.get(1))));
							}
							if (list.get(0).getClass().isInstance(new BigDecimal(0))) {
								restrictions.add(cb.between(
										root.get(root.getModel().getSingularAttribute(key, BigDecimal.class)),
										cb.literal((BigDecimal) list.get(0)), cb.literal((BigDecimal) list.get(1))));
							}
						}
					} else if (entry.getKey().endsWith("notEqual")) {
						key = entry.getKey().substring(0, entry.getKey().length() - 8);
						restrictions.add(cb.notEqual(root.get(key), entry.getValue()));
					} else if (entry.getKey().endsWith("isNull")) {
						key = entry.getKey().substring(0, entry.getKey().length() - 6);
						restrictions.add(cb.isNull(root.get(key)));
					} else if (entry.getKey().endsWith("isNotNull")) {
						key = entry.getKey().substring(0, entry.getKey().length() - 9);
						restrictions.add(cb.isNotNull(root.get(key)));
					} else {
						key = entry.getKey();
						restrictions.add(cb.equal(root.get(key), entry.getValue()));
					}
				}
				query.where(cb.and(restrictions.toArray(new Predicate[restrictions.size()])));
				for (String order : orders) {
					if (order.endsWith("Desc")) {
						key = order.substring(0, order.length() - 4);
						query.orderBy(cb.desc(root.get(key)));
					} else {
						query.orderBy(cb.asc(root.get(order)));
					}
				}
				return null;
			}
		}, pageable);
		return page;
	}

}
