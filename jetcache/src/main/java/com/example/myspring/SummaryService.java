package com.example.myspring;

import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public interface SummaryService{
    @Cached(expire = 3600, cacheType = CacheType.REMOTE)
    @CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600, timeUnit = TimeUnit.SECONDS)
    /**
     * 加载保护 为了防止缓存未命中时的高并发访问打爆数据库
     *  CachePenetrationProtect注解保证当缓存未命中的时候，一个JVM里面只有一个线程去执行方法，其它线程等待结果
     */
    @CachePenetrationProtect
    BigDecimal salesVolumeSummary(int timeId, long catagoryId);
}