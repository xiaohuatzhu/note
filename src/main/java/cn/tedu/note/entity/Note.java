package cn.tedu.note.entity;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("note")
public class Note implements Serializable {
	private static final long serialVersionUID = -6699267497847540276L;

	/** `cn_note_id` varchar(100) NOT NULL COMMENT '笔记ID', */
	private String id;
	/** `cn_notebook_id` varchar(100) DEFAULT NULL COMMENT '笔记本ID', */
	private String notebookId;
	/** `cn_user_id` varchar(100) DEFAULT NULL COMMENT '用户ID', */
	private String userId;
	/** `cn_note_status_id` varchar(100) DEFAULT NULL COMMENT '笔记状态ID:备用', 1正常,0删除*/
	private String statusId;
	/** `cn_note_type_id` varchar(100) DEFAULT NULL COMMENT '笔记本类型ID：备用', 默认0*/
	private String typeId;
	/** `cn_note_title` varchar(500) DEFAULT NULL COMMENT '笔记标题', */
	private String title;
	/** `cn_note_body` text COMMENT '笔记内容', */
	private String body;
	/** `cn_note_create_time` bigint(20) DEFAULT NULL COMMENT '笔记创建时间', */
	private Long createTime;
	/**
	 * `cn_note_last_modify_time` bigint(20) DEFAULT NULL COMMENT '笔记最近修改时间',
	 */
	private Long lastModifyTime;

	public Note() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotebookId() {
		return notebookId;
	}

	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
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
		Note other = (Note) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", notebookId=" + notebookId + ", userId=" + userId + ", statusId=" + statusId
				+ ", typeId=" + typeId + ", title=" + title + ", body=" + body + ", createTime=" + createTime
				+ ", lastModifyTime=" + lastModifyTime + "]";
	}

}
