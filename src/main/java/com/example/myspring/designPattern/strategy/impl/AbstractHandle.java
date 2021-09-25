package com.example.myspring.designPattern.strategy.impl;

import com.example.myspring.common.Result;
import com.example.myspring.designPattern.strategy.in.IStudyHandle;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractHandle implements IStudyHandle {
    @Override
    public Result handleSumbit(HttpServletRequest request) {
        return Result.ok(getType().name());
    }
}
