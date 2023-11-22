package com.example.myspring.polling;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 *
 * 通过 request开启异步上下文
 *
 *
 */

@RestController
@Slf4j
@RequestMapping("/polling2")
public class AsyncRequestServer {

    // com.example.myspring.guava 提供的多值 Map，一个 key 可以对应多个 value
    private volatile Multimap<String, AsyncTask> dataIdContext = Multimaps.synchronizedSetMultimap(HashMultimap.create());

    private ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("longPolling-timeout-checker-%d").build();

    private ScheduledExecutorService timeoutChecker = new ScheduledThreadPoolExecutor(1, threadFactory);

    @Data
    private static class AsyncTask {
        // 长轮询请求的上下文，包含请求和响应体
        private AsyncContext asyncContext;
        // 超时标记
        private boolean timeout;

        public AsyncTask(AsyncContext asyncContext, boolean timeout) {
            this.asyncContext = asyncContext;
            this.timeout = timeout;
        }
    }


    /**
     * http://localhost:8081/polling2/watch?dataId=data001
     * @param request
     * @param response
     */
    @RequestMapping("/watch")
    public void addListener(HttpServletRequest request, HttpServletResponse response) {

        String dataId = request.getParameter("dataId");

        // ② 开启异步
        AsyncContext asyncContext = request.startAsync(request, response);
        AsyncTask asyncTask = new AsyncTask(asyncContext, true);

        dataIdContext.put(dataId, asyncTask);
        // ③ 启动定时器，30s 后写入 304 响应
        timeoutChecker.schedule(() -> {
            if (asyncTask.isTimeout()) {
                dataIdContext.remove(dataId, asyncTask);
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                asyncContext.complete();
            }
        }, 30000, TimeUnit.MILLISECONDS);
    }


    /**
     * http://localhost:8081/polling2/publish?dataId=data001&configInfo=hello
     * @param dataId
     * @param configInfo
     * @return
     */
    @RequestMapping("/publish")
    @SneakyThrows
    public String publishConfig(String dataId, String configInfo) {
        log.info("publish configInfo dataId: [{}], configInfo: {}", dataId, configInfo);
        Collection<AsyncTask> asyncTasks = dataIdContext.removeAll(dataId);

        for (AsyncTask asyncTask : asyncTasks) {
            asyncTask.setTimeout(false);
            HttpServletResponse response = (HttpServletResponse)asyncTask.getAsyncContext().getResponse();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(configInfo);
            asyncTask.getAsyncContext().complete();
        }
        return "success";
    }



}
