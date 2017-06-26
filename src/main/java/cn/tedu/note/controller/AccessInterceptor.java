package cn.tedu.note.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.tedu.note.util.JsonResult;

@Component
public class AccessInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		System.out.println("AccessInterceptor.preHandle()->access:" + req.getRequestURI());
		// 获取session中的loginUser
		HttpSession session = req.getSession();
		Object user = session.getAttribute("loginUser");
		if (user == null) {
			// 未登录,返回错误json
			// 设置浏览器接收响应的内容和编码
			res.setContentType("application/json;charset=utf-8");
			// 设置服务器响应内容给浏览器的编码,即getWriter()的编码
			res.setCharacterEncoding("utf-8");
			JsonResult result = new JsonResult(1, "请重新登录");
			// jackson API , 将对象转换成json语句
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(result);
			// 向浏览器返回json
			res.getWriter().println(json);
			// 建议加上,刷新res中的内容
			res.flushBuffer();
			return false;
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
