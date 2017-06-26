package cn.tedu.note.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.note.entity.User;
import cn.tedu.note.exception.PasswordConfirmException;
import cn.tedu.note.exception.PasswordException;
import cn.tedu.note.exception.PasswordNewException;
import cn.tedu.note.exception.UserNameException;
import cn.tedu.note.exception.UserNotFoundException;
import cn.tedu.note.service.UserService;
import cn.tedu.note.util.JsonResult;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Resource(name = "userService")
	private UserService userService;

	/**
	 * 在其他控制器方法执行出现异常的时候,执行异常处理方法.
	 */
	@ExceptionHandler({ UserNotFoundException.class, PasswordException.class, UserNameException.class,
			PasswordNewException.class, PasswordConfirmException.class })
	@ResponseBody
	public Object exHandler(Exception e) {
		e.printStackTrace();
		if (e instanceof UserNotFoundException) {
			return new JsonResult(2, e);
		} else if (e instanceof PasswordException) {
			return new JsonResult(3, e);
		} else if (e instanceof UserNameException) {
			return new JsonResult(4, e);
		} else if (e instanceof PasswordNewException) {
			return new JsonResult(5, e);
		} else if (e instanceof PasswordConfirmException) {
			return new JsonResult(6, e);
		}
		return new JsonResult(e);
	}

	@RequestMapping("/login.do")
	@ResponseBody
	public Object login(String name, String password, HttpSession session) {
		User user = userService.login(name, password);
		session.setAttribute("loginUser", user);
		return new JsonResult(user);
	}

	@RequestMapping("/logout.do")
	@ResponseBody
	public Object logout(String userId, HttpSession session) {
		userService.logout(userId);
		session.removeAttribute("loginUser");
		return new JsonResult("Logout success");
	}

	@RequestMapping("/regist.do")
	@ResponseBody
	public Object regist(String name, String nick, String password, String confirm) {
		User user = userService.regist(name, nick, password, confirm);
		return new JsonResult(user);
	}

	@RequestMapping("/modifyPassword.do")
	@ResponseBody
	public Object modifyPassword(String name, String password, String newPassword, String confirm) {
		userService.modifyPassword(name, password, newPassword, confirm);
		return new JsonResult("Modify Password Success");
	}

	@RequestMapping("/heartBeat.do")
	@ResponseBody
	public Object heartBeat() {
		System.out.println("UserController.heartBeat()");
		return new JsonResult("HeartBeat");
	}
}
