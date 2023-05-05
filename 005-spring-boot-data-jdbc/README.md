# Spring Boot 集成 Spring Data JDBC

#### 1.简述目的
  + Spring Boot 集成 Spring Data JDBC
  + Spring Data JDBC 使用
    - CURD使用
    - 分页与排序使用
    - 基于方法名查询使用
    - @Query使用
  + 自动执行SQL脚本

#### 2.前提
 * Java 8
 * Maven 3.x
 
#### 3.创建项目
 * 访问：https://start.spring.io/
 * Project 选择 Maven
 * Language 选择 Java
 * Spring Boot 选择 2.7.10
  
#### 4.修改 pom.xml
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jdartifactId>
</dependency>
```

#### 5.修改 application.properties
```
spring.h2.console.enabled=true
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:file:./target/foobar
spring.datasource.username=sa
``` 
#### 6.创建 SQL 脚本
 * 创建文件夹 `src/main/resources/db`
 * 创建文件 `src/main/resources/db/user.sql`
```SQL
DROP TABLE IF EXISTS t_user;
create table t_user (
    ID bigint auto_increment not null,
    NAME varchar(100) not null,
    LASTNAME varchar(100) not null
);
insert into t_user(ID,NAME,LASTNAME) values(-3,'Alice','lastname');
insert into t_user(ID,NAME,LASTNAME) values(-2,'Alice','lastname');
insert into t_user(ID,NAME,LASTNAME) values(-1,'Victor','lastname');
```

#### 7.开启扫描与自动执行脚本
  + 开启 Spring Data JDBC 扫描，添加 `@EnableJdbcRepositories`
  + 自动执行 SQL 脚本，创建 `DataSourceInitializer` bean
  
  最后代码如下：
```java
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@EnableJdbcRepositories
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Value("classpath:db/user.sql")
    private Resource sql;
	
    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        // 设置数据源
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }
    
    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        // 设置字符编码，如果不设置，数据库数据中文乱码
        populator.setSqlScriptEncoding("UTF-8");
            populator.addScripts(sql);
        return populator;
    }
}
```
 

#### 8.使用
 * CURD使用 

继承于 `CrudRepository` 获得基本的CURD能力
```java
public interface UserRepository extends CrudRepository<User, Long>{}
```
 * 分页与排序使用
  
继承于 `UserPagingRepository` 获得的分页与排序能力
```java
public interface UserPagingRepository extends PagingAndSortingRepository<User, Long>{}
```
 * 基于方法名查询使用  

如果查询条件不够丰富，可以使用基于方法名的查询
```java
List<User> findByLastname(String lastname);
Page<User> findByLastname(String lastname, Pageable pageable);
```

 * @Query使用
  
如果需要自己写 SQL 语句查询可以使用 `@Query` 

```java
@Query("select id, name,lastname from t_user u where u.lastname = :lastname")
List<User> findByAnnotation(String lastname);
```

#### 9.测试用例
 
[UserRepositoryTest.java](https://github.com/Peng-star-star/spring-study/blob/main/005-spring-boot-data-jdbc/src/test/java/com/example/demo/repository/UserRepositoryTest.java)

[UserPagingRepositoryTest.java](https://github.com/Peng-star-star/spring-study/blob/main/005-spring-boot-data-jdbc/src/test/java/com/example/demo/repository/UserPagingRepositoryTest.java)

#### 10.参考
 [官网 - Spring Data JDBC - Reference Documentation](https://docs.spring.io/spring-data/jdbc/docs/2.4.11/reference/html)