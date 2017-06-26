package cn.tedu.note.dao;

import java.io.Serializable;

import org.apache.ibatis.annotations.Param;

import cn.tedu.note.entity.User;

public interface UserDao extends Serializable {

	User findUserByName(String name);

	int addUser(User user);

	User findUserById(String userId);

	int countUserByUserId(String userId);

	void modifyPasswordByUserId(@Param("name") String name, @Param("password") String password);

}
