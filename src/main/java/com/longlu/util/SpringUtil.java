package com.longlu.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Administrator
 * springUtil工具类 获取service对象
 * 需要在applicationContext.xml 末尾配置，而且不能在main方法中直接使用，必须启动tomcat加载spring才能使用
 * <bean id="applicationContext" class="com.crm.util.SpringUtil"/>
 */
public class SpringUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }
	public static Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}

}
