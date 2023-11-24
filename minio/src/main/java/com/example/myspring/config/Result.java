package com.example.myspring.config;


import com.google.common.collect.ImmutableMap;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Map;


public class Result<T> implements Serializable {
    private static final long serialVersionUID = -1491499610241557029L;

    private Integer code;
    private String msg;
    private T data;
    private Map info;

    private String traceId;

    public Map getInfo() {
        return info;
    }

    public void setInfo(Map info) {
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.traceId = MDC.get("traceId");
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.traceId = MDC.get("traceId");
    }

    public static Result ok(String msg, Object data) {
        
        return new Result(HttpStatus.OK.value(), msg, data);
    }

    public Result put(String key, Object obj) {
        this.info = ImmutableMap.of(key, obj);
        return this;
    }

    public static Result ok() {
        return new Result(HttpStatus.OK.value(), "ok", null);
    }

    public static Result ok(String msg) {
        return new Result(HttpStatus.OK.value(), msg, null);
    }

    public static Result error() {
        return new Result(HttpStatus.SERVICE_UNAVAILABLE.value(),  "error");
    }

    public static Result error(String msg) {
        return new Result(HttpStatus.SERVICE_UNAVAILABLE.value(), msg);
    }

    public static Result create(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result create(Integer code, String msg) {
        return new Result(code, msg);
    }
}
