package cn.tedu.note.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

/**
 * 
 * CREATE TABLE `cn_notebook` ( `cn_notebook_id` varchar(100) NOT NULL COMMENT
 * '笔记本ID', `cn_user_id` varchar(100) DEFAULT NULL COMMENT '用户ID',
 * `cn_notebook_type_id` varchar(100) DEFAULT NULL COMMENT '笔记本类型ID',
 * `cn_notebook_name` varchar(500) DEFAULT NULL COMMENT '笔记本名',
 * `cn_notebook_desc` text COMMENT '笔记本说明', `cn_notebook_createtime` timestamp
 * NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP, PRIMARY
 * KEY (`cn_notebook_id`), KEY `FK_Note_User_Reference` (`cn_user_id`), KEY
 * `FK_Reference_6` (`cn_notebook_type_id`) ) ENGINE=InnoDB DEFAULT
 * CHARSET=utf8;
 *
 */
@Alias("notebook")
public class Notebook implements Serializable {
	private static final long serialVersionUID = 6645889448531380183L;

	private String id;
	private String userId;
	private String typeId;
	private String name;
	private String desc;
	private Timestamp createTime;

	public Notebook() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Notebook [id=" + id + ", userId=" + userId + ", typeId=" + typeId + ", name=" + name + ", desc=" + desc
				+ ", createTime=" + createTime + "]";
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
		Notebook other = (Notebook) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
