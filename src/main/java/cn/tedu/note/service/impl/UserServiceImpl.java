package cn.tedu.note.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.User;
import cn.tedu.note.exception.PasswordConfirmException;
import cn.tedu.note.exception.PasswordException;
import cn.tedu.note.exception.PasswordNewException;
import cn.tedu.note.exception.UserNameException;
import cn.tedu.note.exception.UserNotFoundException;
import cn.tedu.note.service.UserService;
import cn.tedu.note.util.Md5Util;
import cn.tedu.note.util.StrUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Value("#{const.nameRegex}")
	private String nameRegex;
	@Value("#{const.pwdRegex}")
	private String pwdRegex;

	@Transactional
	public User login(String name, String password) {
		logger.info("UserService.login(" + name + "," + password + ")");
		// if (password == null || password.trim().isEmpty()) {
		// throw new PasswordException("密码为空");
		// }
		if (!password.matches(pwdRegex)) {
			throw new PasswordException("6~12个字符");
		}
		// if (name == null || name.trim().isEmpty()) {
		// throw new UserNotFoundException("用户名为空");
		// }
		if (!name.matches(nameRegex)) {
			throw new UserNotFoundException("4~10个字符");
		}
		User user = userDao.findUserByName(name.trim());
		if (user == null) {
			throw new UserNotFoundException("用户名不存在");
		}
		String pwd = Md5Util.saltString(password.trim());
		if (pwd.equals(user.getPassword())) {
			return user;
		}

		throw new PasswordException("密码错误");
	}

	@Transactional
	public User regist(String name, String nick, String password, String confirm)
			throws UserNameException, PasswordException {
		// 检查name , 不能重复
		// if (StrUtil.isNullOrEmpty(name.trim())) {
		// throw new UserNameException("用户名不能为空");
		// }
		if (!name.matches(nameRegex)) {
			throw new UserNameException("4~10个字符");
		}
		User one = userDao.findUserByName(name.trim());
		if (one != null) {
			throw new UserNameException("该用户名被占用");
		}
		// 检查密码
		// if (StrUtil.isNullOrEmpty(password)) {
		// throw new PasswordException("密码不能为空");
		// }
		if (!password.matches(pwdRegex)) {
			throw new PasswordException("6~12个字符");
		}
		if (!password.equals(confirm)) {
			throw new PasswordException("密码输入不一致");
		}
		// 检查nick
		if (StrUtil.isNullOrEmpty(nick)) {
			nick = name;
		}
		String id = UUID.randomUUID().toString();
		String token = "";
		String pwd = Md5Util.saltString(password);
		User user = new User(id, name, pwd, token, nick);
		int n = userDao.addUser(user);
		if (n != 1) {
			throw new RuntimeException("注册失败");
		}
		return user;
	}

	@Transactional
	public void logout(String userId) {
		if (StrUtil.isNullOrEmpty(userId)) {
			throw new UserNameException("未登录");
		}
		int n = userDao.countUserByUserId(userId);
		if (n != 1) {
			throw new UserNotFoundException("非法用户");
		}
	}

	@Transactional
	public void modifyPassword(String name, String password, String newPassword, String confirm) {
		if (StrUtil.isNullOrEmpty(name)) {
			throw new UserNameException("未登录");
		}
		if (!password.matches(pwdRegex)) {
			throw new PasswordException("6~12个字符");
		}
		if (!password.matches(pwdRegex)) {
			throw new PasswordNewException("6~12个字符");
		}
		if (!newPassword.equals(confirm)) {
			throw new PasswordConfirmException("密码输入不一致");
		}

		User user = userDao.findUserByName(name.trim());
		if (user == null) {
			throw new UserNotFoundException("非法用户");
		}
		String pwd = Md5Util.saltString(password);
		if (!pwd.equals(user.getPassword())) {
			throw new PasswordException("原密码不正确");
		}

		userDao.modifyPasswordByUserId(name, Md5Util.saltString(newPassword));
	}

}
