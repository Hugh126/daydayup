package com.example.myspring;

import com.example.myspring.bean.LifeCycleBean;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class LifeCycleTest {

	@Test
	public void test() throws IOException {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.example.myspring.bean");
		LifeCycleBean myLifeCycleBean = applicationContext.getBean("lifeCycleBean", LifeCycleBean.class);
		System.out.println("12. bean创建完成 name： " + myLifeCycleBean.getName());
		applicationContext.close();
	}



}
