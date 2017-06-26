package test1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.User;

public class UserDaoTest {

	ClassPathXmlApplicationContext ctx;
	UserDao dao;

	@Before
	public void init() {
		ctx = new ClassPathXmlApplicationContext("conf/spring-mvc.xml", "conf/spring-mybatis.xml");
		dao = ctx.getBean("userDao", UserDao.class);
	}

	@After
	public void after() {
		ctx.close();
	}

	@Test
	public void testFindUserByName() {
		User user = dao.findUserByName("demo");
		System.out.println(user);
	}
}
