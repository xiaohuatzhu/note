package test1;

import org.junit.Before;
import org.junit.Test;

import cn.tedu.note.dao.NotebookDao;
import cn.tedu.note.entity.Notebook;

public class NotebookDaoTest extends BaseTest {
	NotebookDao notebookDao;

	@Before
	public void initNotebookDao() {
		notebookDao = ctx.getBean("notebookDao", NotebookDao.class);
	}

	@Test
	public void test1() {
		String notebookId = "0037215c-09fe-4eaa-aeb5-25a340c6b39b";
		Notebook nb = notebookDao.findNotebookByNotebookId(notebookId);
		System.out.println(nb);
	}
}
