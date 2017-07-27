package com.mmall.common;

import ch.qos.logback.classic.Logger;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

public class TokenCache {


    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX="token_";

    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
        //default data loading implement, when call "get" to get value , if "key" don't have according value then call this method to loading.
        // @Override
        public String load(String s) throws Exception {
        return null;
        }
    });

    public static void setKey(String key, String value){
        localCache.put(key,value);
    }

    public static String getKey(String key){
        String value = null;
        try{
            value = localCache.get(key);
            if("null".equals(value)){
                return value;
            }
            return value;
           }catch (Exception e){
            logger.error("localCache get error",e);
        }
        return value;
    }
}