package com.wufeng.spring.DI.demo.action;

import com.wufeng.spring.DI.demo.service.IModifyService;
import com.wufeng.spring.DI.demo.service.IQueryService;
import com.wufeng.spring.DI.springframework.annotation.MyAutowired;
import com.wufeng.spring.DI.springframework.annotation.MyController;
import com.wufeng.spring.DI.springframework.annotation.MyRequestMapping;
import com.wufeng.spring.DI.springframework.annotation.MyRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 公布接口url
 * @author Tom
 *
 */
@MyController
@MyRequestMapping("/web")
public class MyAction {

	@MyAutowired
	IQueryService queryService;
	@MyAutowired
	IModifyService modifyService;

	@MyRequestMapping("/query.json")
	public void query(HttpServletRequest request, HttpServletResponse response,
								@MyRequestParam("name") String name){
		String result = queryService.query(name);
		out(response,result);
	}
	
	@MyRequestMapping("/add*.json")
	public void add(HttpServletRequest request,HttpServletResponse response,
			   @MyRequestParam("name") String name,@MyRequestParam("addr") String addr){
		String result = modifyService.add(name,addr);
		out(response,result);
	}
	
	@MyRequestMapping("/remove.json")
	public void remove(HttpServletRequest request,HttpServletResponse response,
		   @MyRequestParam("id") Integer id){
		String result = modifyService.remove(id);
		out(response,result);
	}
	
	@MyRequestMapping("/edit.json")
	public void edit(HttpServletRequest request,HttpServletResponse response,
			@MyRequestParam("id") Integer id,
			@MyRequestParam("name") String name){
		String result = modifyService.edit(id,name);
		out(response,result);
	}
	
	
	
	private void out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
