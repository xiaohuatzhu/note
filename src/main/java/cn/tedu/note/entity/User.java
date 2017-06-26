package cn.tedu.note.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 
 * CREATE TABLE `cn_user` ( `cn_user_id` varchar(100) NOT NULL COMMENT '用户ID',
 * `cn_user_name` varchar(100) DEFAULT NULL COMMENT '用户名', `cn_user_password`
 * varchar(100) DEFAULT NULL COMMENT '密码', `cn_user_token` varchar(100) DEFAULT
 * NULL COMMENT '令牌', `cn_user_nick` varchar(100) COMMENT '昵称', PRIMARY KEY
 * (`cn_user_id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */

@Alias("user")
public class User implements Serializable {

	private static final long serialVersionUID = -8421782808743390486L;

	private String id;
	private String name;
	private String password;
	private String token;
	private String nick;

	public User() {

	}

	public User(String id, String name, String password, String token, String nick) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.token = token;
		this.nick = nick;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", token=" + token + ", nick=" + nick
				+ "]";
	}

}
