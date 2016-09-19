package com.zz.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zz.auth.biz.IMenuBiz;
import com.zz.auth.view.VMenu;

@Controller
public class HomeController {

	@Autowired
	private IMenuBiz menuBiz;
	
	@RequestMapping("/home")
	@ResponseBody
	public ModelMap menuTree(ModelMap model) {
		List<VMenu> menus = menuBiz.findMenuTree();
		model.addAttribute("menus", menus);
		return model;
	}
}
