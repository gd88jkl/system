package com.zz.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.zz.auth.entity.MenuEntity;
import com.zz.core.persistence.BaseJpaRepository;

public interface MenuRepository extends BaseJpaRepository<MenuEntity,String> {

	@Query("select m from MenuEntity m where m.menu.id is null")
	List<MenuEntity> findMenudIsNull();

}
