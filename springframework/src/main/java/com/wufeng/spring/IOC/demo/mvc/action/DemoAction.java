package com.wufeng.spring.IOC.demo.mvc.action;



import com.yc.demo.service.IDemoService;
import com.yc.framework.annotation.YCAutowired;
import com.yc.framework.annotation.YCController;
import com.yc.framework.annotation.YCRequestMapping;
import com.yc.framework.annotation.YCRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@YCController
@YCRequestMapping("/demo")
public class DemoAction {

  	@YCAutowired
	private IDemoService demoService;

	@YCRequestMapping("/query")
	public void query( HttpServletResponse resp,
					  @YCRequestParam("name") String name){
		String result = "name= " + name;
		try {
			resp.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}
