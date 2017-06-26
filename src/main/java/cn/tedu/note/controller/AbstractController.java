package cn.tedu.note.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.util.JsonResult;

public abstract class AbstractController {
	public Logger logger = Logger.getLogger(UserController.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object exceptionHandler(Exception e) {
		e.printStackTrace();
		return new JsonResult(e);
	}
}
