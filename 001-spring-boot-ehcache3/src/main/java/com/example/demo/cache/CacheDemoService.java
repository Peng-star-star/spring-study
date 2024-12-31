package com.example.demo.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheDemoService {

    @Cacheable(value = "foo", key = "#id")
    public String getName(String id) {
        log.info("从getName方法获取数据，没有从缓存中获取数据");
        return "123";
    }
}
