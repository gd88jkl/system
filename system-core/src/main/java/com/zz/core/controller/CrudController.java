/*
 * Project Name: spring-boot
 * File Name: CRUDAction.java
 * Copyright: Copyright(C) 1985-2016 ZKTeco Inc. All rights reserved.
 */
package com.zz.core.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zz.core.biz.IBaseBiz;
import com.zz.core.exception.BaseException;
import com.zz.core.tools.JSONHelper;
import com.zz.core.utils.StringUtils;
import com.zz.core.view.ResultMessage;

public abstract class CrudController<T, BaseBiz extends IBaseBiz<T>> {

	@Autowired
	protected BaseBiz baseBiz;
	
	@RequestMapping("/findOne")
	@ResponseBody
	public ResultMessage findOne(@RequestParam(value="id", required=true) String id, ModelMap model) {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setData(baseBiz.findOne(id));
		return resultMessage;
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public ResultMessage findAll(ModelMap model) {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setData(baseBiz.findAll());
		return resultMessage;
	}
	
	@RequestMapping("/findPage")
	@ResponseBody
	public ResultMessage findPage(ModelMap model) {
		//TODO
		ResultMessage resultMessage = new ResultMessage();
		return resultMessage;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	public ResultMessage save(@RequestParam(value="content") String content, ModelMap model) {
		ResultMessage resultMessage = new ResultMessage();
		Class<?> entityClass = this.getEntityClass();
		T t = (T) JSONHelper.getObject(content, entityClass);
		resultMessage.setData(baseBiz.save(t));
		return resultMessage;
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public ResultMessage deleteById(@RequestParam(value="id", required=true) String id, ModelMap model) throws BaseException {
		ResultMessage resultMessage = new ResultMessage();
		baseBiz.delete(id);
		return resultMessage;
	}
	
	@RequestMapping("/deleteByIds")
	@ResponseBody
	public ResultMessage deleteByIds(@RequestParam(value="ids", required=true) String ids, ModelMap model) {
		ResultMessage resultMessage = new ResultMessage();
		String[] idsArr = StringUtils.StringToArray(ids, ",");
		baseBiz.deleteByIds(idsArr);
		return resultMessage;
	}
	
	@SuppressWarnings("unchecked")
	private Class<?> getEntityClass() {
		Class<?> cl = this.getClass();
		// 得到泛型类型参数数组就是<>里面的值
		Type[] types = ((ParameterizedType) cl.getGenericSuperclass()).getActualTypeArguments();
		try
		{
			return ((Class<T>) types[0]).newInstance().getClass();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
}
