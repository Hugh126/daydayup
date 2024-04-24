package com.example.myspring;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理逻辑：
 * BlockException -> blockHandler
 * otherException -> fallback
 * [注意]
 * 1. blockHandler，fallback 必须是和原资源方法同样返回值类型
 * 2. 本质上都属于异常处理方法
 *
 * @see SentinelResourceAspect
 */
@Slf4j
@Service
public class CommonService {

    @SentinelResource(value = "commonService1", blockHandler = "blockHandle", fallback = "fallback")
    public String commonService1(){
        if(true) {
            // 触发异常， 熔断测试
            throw new IllegalStateException("熔断测试");
        }
        return "[commonService1]";
    }

    public String blockHandle(BlockException e) throws IOException {
        log.error("BlockException error", e);
        /**
         * 测试：区分异常链路，并处理返回
         */
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if (request.getRequestURI().contains("hello2")) {
                HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.write("hello2 req, pass !");
                writer.flush();
            }
        }
        return "blockHandle";
    }

    public String fallback(Throwable throwable) {
        log.error("fallback error", throwable);
        return "fallback";
    }


}
