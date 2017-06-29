package cn.tedu.note.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.Note;
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
	@Value("#{const.pageSize}")
	private int pageSize;

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

	@Transactional
	public Object[] downloadNotebook(String userId) throws IOException {
		List<Map<String, Object>> notebooks = listNotebooks(userId);
		Object[] objs = new Object[2];
		objs[0] = userDao.findUserById(userId).getName() + "-云笔记";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		HSSFWorkbook book = new HSSFWorkbook();
		for (Map<String, Object> notebook : notebooks) {
			String name = notebook.get("name").toString();
			String notebookId = notebook.get("id").toString();
			HSSFSheet sheet = book.createSheet(name);
			HSSFRow row0 = sheet.createRow(0);
			HSSFCell cell0 = row0.createCell(0);
			HSSFCell cell1 = row0.createCell(1);
			HSSFCell cell2 = row0.createCell(2);
			HSSFCell cell3 = row0.createCell(3);
			cell0.setCellValue("笔记标题");
			cell1.setCellValue("创建时间");
			cell2.setCellValue("最后修改时间");
			cell3.setCellValue("内容");
			List<Note> notes = noteDao.findAllByNotebookId(notebookId);
			for (int i = 0; i < notes.size(); i++) {
				Note note = notes.get(i);
				HSSFRow row = sheet.createRow(i + 1);
				HSSFCell c1 = row.createCell(0);
				HSSFCell c2 = row.createCell(1);
				HSSFCell c3 = row.createCell(2);
				HSSFCell c4 = row.createCell(3);
				c1.setCellValue(note.getTitle());
				c2.setCellValue(sdf.format(new Date(note.getCreateTime())));
				c3.setCellValue(sdf.format(new Date(note.getLastModifyTime())));
				c4.setCellValue(note.getBody());
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		book.write(out);
		out.close();
		objs[1] = out.toByteArray();
		return objs;
	}

	public List<Map<String, Object>> listNotebooks(String userId, Integer page) {
		checkUserId(userId);

		if (page == null) {
			page = 0;
		}

		int start = page * pageSize;
		String table = "cn_notebook";

		List<Map<String, Object>> list = notebookDao.findNotebooksByPage(userId, start, pageSize, table);

		return list;
	}
}
