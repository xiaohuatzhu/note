package cn.tedu.note.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.Notebook;
import cn.tedu.note.entity.User;
import cn.tedu.note.exception.NotebookException;
import cn.tedu.note.exception.NotebookNameExistedException;
import cn.tedu.note.exception.NotebookNotEmptyException;
import cn.tedu.note.exception.NotebookNotExistedException;
import cn.tedu.note.exception.UserException;
import cn.tedu.note.exception.UserNotFoundException;
import cn.tedu.note.service.NotebookService;
import cn.tedu.note.util.StrUtil;

@Service("notebookService")
public class NotebookServiceImpl implements NotebookService {

	private static final long serialVersionUID = -7140143758412498780L;

	@Resource
	private NotebookDao notebookDao;
	@Resource
	private UserDao userDao;
	@Resource
	private NoteDao noteDao;

	@Transactional
	public List<Map<String, Object>> listNotebooks(String userId) throws UserNotFoundException {
		checkUserId(userId);

		List<Map<String, Object>> list = notebookDao.findNotebooksByUserId(userId);

		return list;
	}

	@Transactional
	public Notebook createNotebook(String userId, String name) {
		checkNotebookName(userId, name);

		checkUserId(userId);

		Notebook notebook = new Notebook();
		notebook.setUserId(userId);
		notebook.setName(name);
		notebook.setId(UUID.randomUUID().toString());
		notebook.setDesc("");
		notebook.setTypeId("5");

		notebookDao.addNotebook(notebook);
		return notebook;
	}

	@Transactional
	public int renameNotebook(String userId, String notebookId, String name) {
		checkNotebookName(userId, name);

		checkNotebookId(userId, notebookId);

		checkUserId(userId);

		Notebook notebook = new Notebook();
		notebook.setUserId(userId);
		notebook.setId(notebookId);
		notebook.setName(name);

		int n = notebookDao.updateNotebook(notebook);

		return n;
	}

	private void checkNotebookId(String userId, String notebookId) {
		if (StrUtil.isNullOrEmpty(notebookId)) {
			throw new NotebookException("未选中笔记本");
		}

		int n = notebookDao.countNotebookByNotebookIdUserId(notebookId, userId);
		if (n != 1) {
			throw new NotebookNotExistedException("未选中笔记本");
		}
	}

	private void checkNotebookName(String userId, String name) {
		if (StrUtil.isNullOrEmpty(name)) {
			throw new NotebookException("笔记本名称不能为空");
		}

		int n = notebookDao.countNotebookByNameUserId(userId, name);
		if (n != 0) {
			throw new NotebookNameExistedException("笔记本名称已存在");
		}
	}

	private void checkUserId(String userId) {
		if (StrUtil.isNullOrEmpty(userId)) {
			throw new UserException("未登录");
		}

		User user = userDao.findUserById(userId);
		if (user == null) {
			throw new UserNotFoundException("用户名不存在");
		}
	}

	@Transactional
	public int deleteNotebook(String userId, String notebookId) {
		checkUserId(userId);

		checkNotebookId(userId, notebookId);

		int n1 = noteDao.countNoteByNotebookId(notebookId);
		if (n1 != 0) {
			throw new NotebookNotEmptyException("请先删除笔记本中的笔记");
		}

		int n = notebookDao.deleteNotebookByNotebookId(notebookId);

		return n;
	}

}
