package com.example.myspring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 // 控制台的输出结果
 1. postProcessBeforeInstantiation被调用
 2. 构造方法被调用，name：小小
 3. postProcessAfterInstantiation被调用
 4. postProcessProperties被调用
 5. BeanNameAware被调用, 获取到的beanName：lifeCycleBean
 6. BeanFactoryAware被调用，获取到beanFactory：org.springframework.beans.factory.support.DefaultListableBeanFactory@117e949d: defining beans [lifeCycleBean,lifeCycleBeanPostProcessor]; root of factory hierarchy
 7. ApplicationContextAware被调用，获取到ApplicationContextAware：org.springframework.context.support.ClassPathXmlApplicationContext@71e9ddb4, started on Sat Feb 22 20:30:35 CST 2020
 8. postProcessBeforeInitialization被调用，把name改成中中
 9. afterPropertiesSet被调用
 10. myInit自定义初始化方法被调用，name：中中
 11. postProcessAfterInitialization被调用，把name改成大大
 12. bean创建完成 name： 大大
 13. DisposableBean被调用
 14. destroy-method自定义销毁方法被调用

 */
@Component
public class LifeCycleBean implements
		BeanNameAware,
		BeanFactoryAware,
		ApplicationContextAware,
		InitializingBean,
		DisposableBean {

	private BeanFactory beanFactory;

	private ApplicationContext applicationContext;

	private String name;

	public LifeCycleBean() {
		this.name="小小";
		System.out.println("2. 构造方法被调用，name：" + name);
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("5. BeanNameAware被调用, 获取到的beanName：" + name);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
		System.out.println("6. BeanFactoryAware被调用，获取到beanFactory：" + beanFactory);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		System.out.println("7. ApplicationContextAware被调用，获取到ApplicationContextAware：" + applicationContext);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("9. afterPropertiesSet被调用");
	}

	@PostConstruct
	public void myInit() {
		System.out.println("10. myInit自定义初始化方法被调用，name：" + getName());
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("14. DisposableBean被调用");
	}

	@PreDestroy
	public void myDestroy() {
		System.out.println("13. destroy-method自定义销毁方法被调用");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
