package com.kingmon.base.common.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.common.log.service.ILogErrorClobService;
import com.kingmon.project.common.log.service.ILogErrorService;

@Component
@Scope
@Aspect
public class LogAspect {
	@Autowired
	private ILogErrorService logErrorService;

	@Autowired
	private ILogErrorClobService logErrorClobService;


	@AfterThrowing(pointcut = "within(com.kingmon..*)", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		// 添加错误日志
		logErrorService.addLogError(joinPoint, e);
		// 添加clob错误日志
		logErrorClobService.addLogErrorClob(joinPoint, e);
	}

	/**
	 * 获取注解中对方法的描述信息 (基于反射)
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 *             String
	 *
	 */
	public static String getServiceMthodDescription(JoinPoint joinPoint)
			throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class<?> targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class<?>[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(AuthWidgetRule.class)
							.desc();
					break;
				}
			}
		}
		return description;
	}
}
