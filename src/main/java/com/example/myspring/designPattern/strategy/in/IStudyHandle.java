package com.example.myspring.designPattern.strategy.in;

import com.example.myspring.common.Result;
import com.example.myspring.designPattern.strategy.StudyEnum;

import javax.servlet.http.HttpServletRequest;

public interface IStudyHandle {
    public StudyEnum getType();
    public Result handleSumbit(HttpServletRequest request);
}
