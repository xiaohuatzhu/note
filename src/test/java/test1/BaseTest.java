package test1;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseTest {

	protected ClassPathXmlApplicationContext ctx;

	@Before
	public void initBase() {
		System.out.println("BaseTest.init()");
		ctx = new ClassPathXmlApplicationContext("conf/spring-service.xml", "conf/spring-mvc.xml",
				"conf/spring-mybatis.xml");
		System.out.println(ctx);
	}

	@After
	public void after() {
		System.out.println("BaseTest.after()");
		ctx.close();
	}

}