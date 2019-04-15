package com.wufeng.spring.DI.demo.service.impl;

import com.wufeng.spring.DI.demo.service.IModifyService;
import com.wufeng.spring.DI.springframework.annotation.MyService;


@MyService
public class ModifyService implements IModifyService {

	@Override
	public String add(String name,String addr) {
		return "modifyService add,name=" + name + ",addr=" + addr;
	}

	@Override
	public String edit(Integer id,String name) {
		return "modifyService edit,id=" + id + ",name=" + name;
	}

	@Override
	public String remove(Integer id) {
		return "modifyService id=" + id;
	}
	
}
