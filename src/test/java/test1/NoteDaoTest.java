package test1;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.entity.Note;

public class NoteDaoTest extends BaseTest {

	NoteDao noteDao;

	@Before
	public void initNoteDao() {
		noteDao = ctx.getBean("noteDao", NoteDao.class);
	}

	@Test
	public void test1() {
		String notebookId = "6d763ac9-dca3-42d7-a2a7-a08053095c08";
		List<Map<String, Object>> list = noteDao.findNotesByNotebookId(notebookId);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}

	@Test
	public void test2() {
		String noteId = "09f60aeb-a573-4fcf-b39f-903e1536e762";
		String body = noteDao.findNoteBodyByNoteId(noteId);
		System.out.println(body);
	}

	@Test
	public void test3() {
		String noteId = "09f6";
		int n = noteDao.countNotesByNoteId(noteId, "1");
		System.out.println(n);
	}

	@Test
	public void test4() {
		String noteId = "003ec2a1-f975-4322-8e4d-dfd206d6ac0c";
		Note note = noteDao.findNoteByNoteId(noteId);
		System.out.println(note);
		note.setTitle("test(4)");
		note.setBody("笔记笔记。。。test(4)");
		noteDao.updateNote(note);
		Note note1 = noteDao.findNoteByNoteId(noteId);
		System.out.println(note1);
	}
}
