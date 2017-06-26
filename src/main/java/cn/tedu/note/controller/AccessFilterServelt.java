package cn.tedu.note.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AccessFilterServelt implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 登录和弹出框都放行
		String login = "/log_in.html";
		String alert = ".*alert_.*.html";
		// 强转
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		// 获取请求的uri 项目名/路径名
		String uri = req.getRequestURI();
		System.out.println("AccessFilterServelt.doFilter()->access:" + uri);
		if (uri.endsWith(login) || uri.matches(alert)) {
			// 登录和弹出框都放行
			chain.doFilter(req, res);
			return;
		}
		// 获取session中的user
		Object user = session.getAttribute("loginUser");
		if (user == null) {
			// 未登录,转发到登录界面,此处要用全路径 项目名/路径名
			res.sendRedirect(req.getContextPath() + "/log_in.html");
			// 此处要return, 不然和后面的响应一起会导致二次响应异常
			// 规定一次请求只能有一次响应,不然会抛出异常
			return;
		}
		// 已登录,放行
		chain.doFilter(req, res);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
