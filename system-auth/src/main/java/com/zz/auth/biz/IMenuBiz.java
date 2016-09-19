package com.zz.auth.biz;

import java.util.List;

import com.zz.auth.entity.MenuEntity;
import com.zz.auth.view.VMenu;
import com.zz.core.biz.IBaseBiz;

public interface IMenuBiz extends IBaseBiz<MenuEntity> {

	List<VMenu> findMenuTree();

}
