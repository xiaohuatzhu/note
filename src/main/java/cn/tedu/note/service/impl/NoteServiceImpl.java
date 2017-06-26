package cn.tedu.note.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.Notebook;
import cn.tedu.note.entity.User;
import cn.tedu.note.exception.NoteException;
import cn.tedu.note.exception.NoteTitleException;
import cn.tedu.note.exception.NoteTitleExistedException;
import cn.tedu.note.exception.NotebookException;
import cn.tedu.note.exception.NotebookNotExistedException;
import cn.tedu.note.exception.UserException;
import cn.tedu.note.exception.UserNotFoundException;
import cn.tedu.note.service.NoteService;
import cn.tedu.note.util.StrUtil;

@Service("noteService")
public class NoteServiceImpl implements NoteService {
	private static final long serialVersionUID = -8047042216234840700L;

	@Resource
	private NoteDao noteDao;
	@Resource
	private NotebookDao notebookDao;
	@Resource
	private UserDao userDao;

	@Transactional(readOnly = true)
	public List<Map<String, Object>> listNotes(String notebookId) {

		if (notebookId == null || notebookId.trim().isEmpty()) {
			throw new RuntimeException("NotebookId 不能为空");
		}

		Notebook notebook = notebookDao.findNotebookByNotebookId(notebookId);
		if (notebook == null) {
			throw new RuntimeException("笔记本不存在:" + notebookId);
		}

		List<Map<String, Object>> list = noteDao.findNotesByNotebookId(notebookId);

		return list;
	}

	@Transactional(readOnly = true)
	public String getNoteBodyByNoteId(String noteId) {

		if (StrUtil.isNullOrEmpty(noteId)) {
			throw new RuntimeException("NoteId 不能为空");
		}

		String body = noteDao.findNoteBodyByNoteId(noteId);

		if (body == null) {
			throw new RuntimeException("获取笔记内容失败");
		}

		return body;
	}

	@Transactional
	public Note updateNote(Note note) {
		String id = note.getId();
		String title = note.getTitle();
		// String body = note.getBody();
		if (StrUtil.isNullOrEmpty(id)) {
			throw new RuntimeException("NoteId 不能为空");
		}
		if (title != null && title.trim().isEmpty()) {
			throw new RuntimeException("标题不能为空");
		}
		/*
		 * if (body == null) { throw new RuntimeException("未指定笔记内容"); }
		 */

		int n1 = noteDao.countNotesByNoteId(id, "1");
		if (n1 == 0) {
			throw new RuntimeException("笔记不存在");
		}

		int n = noteDao.countNoteByTitleNotebookIdUserId(note);
		if (n != 0) {
			throw new RuntimeException("标题已存在");
		}

		noteDao.updateNote(note);

		return note;
	}

	@Transactional
	public Note createNote(Note note) {
		String title = note.getTitle();
		if (StrUtil.isNullOrEmpty(title)) {
			throw new NoteTitleException("标题不能为空");
		}
		String notebookId = note.getNotebookId();
		if (StrUtil.isNullOrEmpty(notebookId)) {
			throw new NotebookException("未选中笔记本");
		}
		String userId = note.getUserId();
		if (StrUtil.isNullOrEmpty(userId)) {
			throw new UserNotFoundException("未登录");
		}

		int n1 = noteDao.countNoteByTitleNotebookIdUserId(note);
		if (n1 != 0) {
			throw new NoteTitleExistedException("标题重复");
		}

		int n2 = notebookDao.countNotebooks(notebookId);
		if (n2 != 1) {
			throw new NotebookNotExistedException("笔记本不存在");
		}

		int n3 = userDao.countUserByUserId(userId);
		if (n3 != 1) {
			throw new UserException("用户名不存在");
		}

		int n4 = notebookDao.countNotebookByNotebookIdUserId(notebookId, userId);
		if (n4 != 1) {
			throw new RuntimeException("用户名与笔记本不对应");
		}

		note.setId(UUID.randomUUID().toString());
		note.setStatusId("1");
		note.setTypeId("1");
		note.setBody("");

		noteDao.addNote(note);

		return note;
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> listDeleteNotes(String userId) {
		checkUserId(userId);

		List<Map<String, Object>> list = noteDao.findDeleteNotes(userId);

		return list;
	}

	@Transactional
	public int deleteNote(String noteId) {
		checkNormalNoteId(noteId);
		Note note = new Note();
		note.setId(noteId);
		note.setStatusId("0");
		int n = noteDao.updateNote(note);
		if (n != 1) {
			throw new RuntimeException("删除失败");
		}
		return n;
	}

	@Transactional
	public int deleteDelNote(String noteId) {
		if (StrUtil.isNullOrEmpty(noteId)) {
			throw new NoteException("未选定笔记");
		}

		int n1 = noteDao.countNotesByNoteId(noteId, "0");
		if (n1 != 1) {
			throw new NoteException("笔记不存在");
		}

		int n = noteDao.deleteDelNote(noteId);
		if (n != 1) {
			throw new RuntimeException("删除失败");
		}
		return n;
	}

	@Transactional
	public Note moveNote(String noteId, String notebookId) {
		checkNormalNoteId(noteId);

		if (StrUtil.isNullOrEmpty(notebookId)) {
			throw new NotebookException("未选中笔记本");
		}

		Notebook notebook = notebookDao.findNotebookByNotebookId(notebookId);
		if (notebook == null) {
			throw new RuntimeException("笔记本不存在:" + notebookId);
		}

		Note note = new Note();
		note.setId(noteId);
		note.setNotebookId(notebookId);
		// 加上这句后,在回收站的笔记恢复到笔记本时,也可以这段代码
		// 因为移动到指定笔记本的笔记,移动后的状态必然为1

		noteDao.updateNote(note);

		return note;
	}

	private void checkNormalNoteId(String noteId) {
		if (StrUtil.isNullOrEmpty(noteId)) {
			throw new NoteException("未选定笔记");
		}

		int n1 = noteDao.countNotesByNoteId(noteId, "1");
		if (n1 != 1) {
			throw new NoteException("笔记不存在");
		}
	}

	@Transactional
	public Note replayNote(String noteId, String notebookId, String title) {
		if (StrUtil.isNullOrEmpty(noteId)) {
			throw new NoteException("未选定笔记");
		}

		if (StrUtil.isNullOrEmpty(notebookId)) {
			throw new NotebookException("未选中笔记本");
		}

		int n1 = noteDao.countNotesByNoteId(noteId, "0");
		if (n1 != 1) {
			throw new NoteException("笔记不存在");
		}

		int n2 = noteDao.countNoteByTitleNotebookId(notebookId, title);
		while (n2 != 0) {
			title = StrUtil.reName(title);
			n2 = noteDao.countNoteByTitleNotebookId(notebookId, title);
		}

		Notebook notebook = notebookDao.findNotebookByNotebookId(notebookId);
		if (notebook == null) {
			throw new RuntimeException("笔记本不存在:" + notebookId);
		}

		System.out.println("title:" + title);

		Note note = new Note();
		note.setId(noteId);
		note.setNotebookId(notebookId);
		note.setTitle(title);
		note.setStatusId("1");

		noteDao.updateNote(note);

		return note;
	}

	@Transactional
	public int deleteNotes(String... noteIds) {
		int len = noteIds.length;
		if (len == 0) {
			throw new NoteException("未选中笔记");
		}
		for (int i = 0; i < len; i++) {
			String id = noteIds[i];
			Note note = noteDao.findNoteByNoteId(id);
			if (note == null) {
				throw new NoteException("无效的笔记");
			}
			note.setStatusId("0");
			int n = noteDao.updateNote(note);
			if (n != 1) {
				throw new NoteException("删除失败");
			}
		}
		return len;
	}

	@Transactional
	public int uploadNote(MultipartFile noteFile, String data) throws IOException {
		if (noteFile == null) {
			throw new NoteException("未选择文件");
		}
		if (data == null || !data.contains(",")) {
			throw new NoteException("无法上传");
		}
		String[] strs = data.split(",");
		String userId = strs[0];
		String notebookId = strs[1];
		checkUserId(userId);
		checkNotebookId(userId, notebookId);

		String fileName = noteFile.getOriginalFilename();
		int dot = fileName.lastIndexOf(".");
		String title = dot == -1 ? fileName : fileName.substring(0, dot);
		String id = UUID.randomUUID().toString();
		String body = new String(noteFile.getBytes());

		int n2 = noteDao.countNoteByTitleNotebookId(notebookId, title);
		while (n2 != 0) {
			title = StrUtil.reName(title);
			n2 = noteDao.countNoteByTitleNotebookId(notebookId, title);
		}

		Note note = new Note();
		note.setId(id);
		note.setTitle(title);
		note.setUserId(userId);
		note.setNotebookId(notebookId);
		note.setStatusId("1");
		note.setTypeId("1");
		note.setBody(body);

		int n = noteDao.addNote(note);
		System.out.println(n);
		if (n != 1) {
			throw new NoteException("上传失败");
		}

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

	private void checkUserId(String userId) {
		if (StrUtil.isNullOrEmpty(userId)) {
			throw new UserException("未登录");
		}

		User user = userDao.findUserById(userId);
		if (user == null) {
			throw new UserNotFoundException("用户名不存在");
		}
	}

	/***
	 * 
	 * @return Object[0] title笔记名称, Object[1] byte[]笔记内容的bytes
	 * @throws UnsupportedEncodingException
	 */
	@Transactional(readOnly = true)
	public Object[] downloadNote(String userId, String noteId) throws UnsupportedEncodingException {
		checkUserId(userId);
		checkNormalNoteId(noteId);

		Note note = noteDao.findNoteByNoteId(noteId);
		String title = note.getTitle();
		String body = note.getBody();
		byte[] bs = body.getBytes("utf-8");
		return new Object[] { title, bs };
	}

}
