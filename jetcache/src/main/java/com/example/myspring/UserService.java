package com.example.myspring;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;



public interface UserService {

    @Cached(name="userCache-", key="#userId", cacheType = CacheType.BOTH,expire = 30)
    User getUserById(long userId);

    @CacheUpdate(name="userCache-", key="#user.userId", value="#user")
    void updateUser(User user);

    @CacheInvalidate(name="userCache-", key="#userId")
    void deleteUser(long userId);

}