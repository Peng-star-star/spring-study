package com.example.demo;

import com.example.demo.cache.CacheDemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private CacheDemoService cacheDemoService;

    @Test
    public void getByAnnotation() {
        // 第一次从getName方法获取数据
        System.err.println(cacheDemoService.getName("key001"));
        // 第二次从缓存中获取数据
        System.err.println(cacheDemoService.getName("key001"));
    }

    @Test
    public void getByFunction(){
        System.err.println(cacheManager);//打印CacheManager实现类
        Cache cache = cacheManager.getCache("foo");
        System.err.println(cache.get("key001",String.class));//缓存内没有数据，打印null
        cache.put("key001","123");
        System.err.println(cache.get("key001",String.class));//缓存内有数据，打印123
    }
}
