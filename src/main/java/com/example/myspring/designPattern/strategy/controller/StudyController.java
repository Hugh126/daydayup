package com.example.myspring.designPattern.strategy.controller;

import com.example.myspring.common.Result;
import com.example.myspring.designPattern.strategy.StudyEnum;
import com.example.myspring.designPattern.strategy.StudyHandlerFactory;
import com.example.myspring.designPattern.strategy.in.IStudyHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/study")
public class StudyController {

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

}
