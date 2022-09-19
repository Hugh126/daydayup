package com.example.myspring.designPattern.strategy.controller;

import com.example.myspring.common.Result;
import com.example.myspring.designPattern.strategy.StudyEnum;
import com.example.myspring.designPattern.strategy.StudyHandlerFactory;
import com.example.myspring.designPattern.strategy.in.IStudyHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/study")
public class StudyController implements InitializingBean,  SmartInitializingSingleton {

    private String info;

    public StudyController() {
        System.out.println("--constructor--");
    }

    @PostConstruct
    public void init() {
        System.out.println("--PostConstruct--,info=" + info);
        this.info = "PostConstruct";
    }

    @Autowired
    private StudyHandlerFactory factory;

    @RequestMapping("/{type}")
    public Result handleChinese(@PathVariable String type, HttpServletRequest request) {
        StudyEnum studyEnum = StudyEnum.getWithValue(type);
        Assert.notNull(studyEnum, "type 错误");
        IStudyHandle handle = factory.createHandle(studyEnum);
        log.warn(handle.getClass().getName());
        return handle.handleSumbit(request);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("--afterPropertiesSet--,info=" + info);
        this.info = "afterPropertiesSet";
    }


    /**
     * 在对所有单例对象初始化完毕后，做一些后置的业务处理
     */
    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("--afterSingletonsInstantiated--info=" + info);
    }


}
