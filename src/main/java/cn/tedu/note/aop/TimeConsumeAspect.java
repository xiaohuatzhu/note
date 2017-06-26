package cn.tedu.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/** AOP切面 */
@Component
@Aspect
public class TimeConsumeAspect {
	/**
	 * * 环绕通知 方法:
	 * 
	 * 1. 必须有返回值值Object
	 * 
	 * 2. 必须有参数 ProceedingJoinPoint
	 * 
	 * 3. 必须抛出异常 Throwable
	 * 
	 * 4. 需要在方法中调用 jp.proceed()
	 * 
	 * 5. 返回业务方法的返回值
	 */
	@Around("bean(*Service)")
	public Object calTime(ProceedingJoinPoint jp) throws Throwable {
		long t1 = System.currentTimeMillis();
		Object val = jp.proceed();
		long t2 = System.currentTimeMillis();
		Signature sig = jp.getSignature();
		System.out.println("TimeConsumeAspect.calTime()->" + sig + (t2 - t1) + "ms");
		return val;
	}

}
