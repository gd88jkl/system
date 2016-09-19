package com.zz.auth.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zz.auth.biz.IMenuBiz;
import com.zz.auth.entity.MenuEntity;
import com.zz.auth.repository.MenuRepository;
import com.zz.auth.view.VMenu;
import com.zz.core.biz.impl.BaseBizImpl;
import com.zz.core.persistence.BaseJpaRepository;

@Service
public class MenuBizImpl extends BaseBizImpl<MenuEntity, BaseJpaRepository<MenuEntity, String>> implements IMenuBiz {

	@Autowired
	private MenuRepository menuRepository;
	
	@Override
	public List<VMenu> findMenuTree() {
		List<MenuEntity> lists = menuRepository.findMenudIsNull();
		List<VMenu> menus = new ArrayList<VMenu>();
		for (MenuEntity menu : lists) {
			getMenuSource(menus,menu);
		}
		return menus;
	}
	
	private void getMenuSource(List<VMenu> lists, MenuEntity menuEntity) {
		VMenu menu = new VMenu();
		menu.setId(menuEntity.getId());
		menu.setText(menuEntity.getText());
		menu.setSpriteCssClass(menuEntity.getSpriteCssClass() == null ? "" : menuEntity.getSpriteCssClass());
		List<VMenu> childrenMenu = new ArrayList<VMenu>();
		List<MenuEntity> children = menuEntity.getChildren();
		if(children != null && !children.isEmpty()) {
			for (MenuEntity _menuEntity : children) {
				getMenuSource(childrenMenu,_menuEntity);
			}
			if(!childrenMenu.isEmpty()) {
				menu.setItems(childrenMenu);
			}
		} else {
			menu.setUrl(menuEntity.getUrl());
		}
		lists.add(menu);
	}

}
