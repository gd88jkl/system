package com.zz.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zz.core.biz.IBaseBiz;
import com.zz.core.biz.IUserBiz;
import com.zz.core.entity.User;

@Controller
public class HelloWorldController extends CrudController<User, IBaseBiz<User>> {
	
	@Autowired
	private IUserBiz userBiz;
	
	//@RequestMapping("index")
	//public String index(ModelMap model) {
	//	model.addAttribute("name", "对方是个");
	//	User user = new User();
	//	user.setName("456");
	//	userBiz.save(user);
	//	return "home";
	//}
	//
	//@RequestMapping("index2")
	//public String index2() {
	//	return "index2";
	//}
	
	@RequestMapping("index3")
	public String index3() {
		return "index3";
	}
	
	@RequestMapping("index4")
	public String index4() {
		return "index4";
	}
	
}
