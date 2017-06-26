package cn.tedu.note.service;

import cn.tedu.note.entity.User;
import cn.tedu.note.exception.PasswordException;
import cn.tedu.note.exception.UserNameException;
import cn.tedu.note.exception.UserNotFoundException;

public interface UserService {

	/**
	 * 
	 * @param name
	 *            用户名
	 * @param password
	 *            密码
	 * @return 登录成功返回User对象
	 */
	User login(String name, String password) throws UserNotFoundException, PasswordException;

	/**
	 * UserService 中添加注册功能
	 * 
	 * @param name
	 * @param nick
	 * @param password
	 * @param confirm
	 * @return 注册成功的用户信息
	 * @throws UserNameException
	 *             用户名异常
	 * @throws PasswordException
	 *             密码异常
	 */
	User regist(String name, String nick, String password, String confirm) throws UserNameException, PasswordException;

	void logout(String userId);

	void modifyPassword(String name, String password, String newPassword, String confirm);
}
