package cn.tedu.note.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Notebook;
import cn.tedu.note.exception.UserNotFoundException;

public interface NotebookService extends Serializable {

	List<Map<String, Object>> listNotebooks(String userId) throws UserNotFoundException;

	Notebook createNotebook(String userId, String name);

	int renameNotebook(String userId, String notebookId, String name);

	int deleteNotebook(String userId, String notebookId);
	Object[] downloadNotebook(String userId) throws IOException;

	List<Map<String, Object>> listNotebooks(String userId, Integer page);
}
