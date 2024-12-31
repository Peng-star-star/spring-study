# 001-spring-boot-ehcache3

## 1.集成

仅需4步就可以将 Ehcache 集成到 Spring Boot。

#### 1.添加依赖
```
<!-- 去掉 spring-boot-starter-cache 并不影响使用，但是 spring 文档里特别提示需要添加 -->
<!--  https://docs.spring.io/spring-boot/reference/io/caching.html -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
<dependency>
    <groupId>org.ehcache</groupId>
    <artifactId>ehcache</artifactId>
</dependency>
<dependency>
    <groupId>javax.cache</groupId>
    <artifactId>cache-api</artifactId>
</dependency>
```
#### 2.添加Ehcache配置
```
spring.cache.jcache.config=classpath:ehcache.xml
``` 
#### 3.ehcache.xml文件
```
<config xmlns='http://www.ehcache.org/v3'>
    <cache alias="foo">
        <key-type>java.lang.String</key-type>
        <value-type>java.lang.String</value-type>
        <resources>
            <heap unit="entries">20</heap>
            <offheap unit="MB">10</offheap>
        </resources>
    </cache>
</config>
``` 
#### 4.启动文件添加@EnableCaching注解
```
@EnableCaching
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
``` 

## 2.使用

#### 2.1.注解方式
```
@Slf4j
@Service
public class CacheDemoService {
    @Cacheable(value = "foo", key = "#id")
    public String getName(String id) {
        log.info("从getName方法获取数据，没有从缓存中获取数据");
        return "123";
    }
}
```
测试用例
```
    @Autowired
    private CacheDemoService cacheDemoService;
    @Test
    public void getByAnnotation() {
        // 第一次从getName方法获取数据
        System.err.println(cacheDemoService.getName("key001"));
        // 第二次从缓存中获取数据
        System.err.println(cacheDemoService.getName("key001"));
    }
```
#### 2.2.代码方式 
测试用例
```
    @Autowired
    private CacheManager cacheManager;
    @Test
    public void getByFunction(){
        System.err.println(cacheManager);//打印CacheManager实现类
        Cache cache = cacheManager.getCache("foo");
        System.err.println(cache.get("key001",String.class));//缓存内没有数据，打印null
        cache.put("key001","123");
        System.err.println(cache.get("key001",String.class));//缓存内有数据，打印123
    }
```

项目代码：https://github.com/Peng-star-star/spring-study