package cn.tedu.note.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.tedu.note.entity.Notebook;

public interface NotebookDao extends Serializable {

	List<Map<String, Object>> findNotebooksByUserId(String userId);

	int countNotebooks(String notebookId);

	int countNotebookByNotebookIdUserId(@Param("notebookId") String notebookId, @Param("userId") String userId);

	Notebook findNotebookByNotebookId(String notebookId);

	void addNotebook(Notebook notebook);

	int countNotebookByNameUserId(@Param("userId") String userId, @Param("name") String name);

	int updateNotebook(Notebook notebook);

	int deleteNotebookByNotebookId(String notebookId);
}
