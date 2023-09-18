# spring-boot-自动执行SQL脚本

#### 1.前言
  今天测试同事抱怨，每次部署程序都要执行一个单独的 SQL 脚本很麻烦，问我能不能在程序启动时，自动执行 SQL 脚本。乐于助人的我，肯定不能拒绝了！安排。
  
#### 2.目的
  使用`DataSourceInitializer`来实现 spring-boot 启动时自动执行SQL脚本。

#### 3.创建项目
  * 访问：https://start.spring.io/
  * Project 选择 Maven
  * Language 选择 Java
  * Spring Boot 选择 2.7.15
  * Artifact 填写 001-spring-boot-automatically-execute-sql
  * Package name 填写 com.example.demo
  * Packaging 选择 Jar
  * Java 选择 8  

#### 4.修改 pom.xml
```xml
<dependencies>
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
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>
</dependencies>
```
#### 5.修改 application.properties
```
spring.h2.console.enabled=true
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:demo
spring.datasource.username=sa
``` 

#### 6.编写脚本
在 src/main/resources 创建 demo.sql 文件，内容如下：
```sql
CREATE TABLE IF NOT EXISTS test
(
    id              INT         NOT NULL,
    title           VARCHAR(50) NOT NULL,
    author          VARCHAR(20) NOT NULL
);
```

#### 7.编写 java 文件
在 com.example.demo 创建 AutomaticallyExecuteSQL 文件，内容如下：
```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class AutomaticallyExecuteSQL {

    @Value("classpath:demo.sql")
    private Resource demoScript;

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
        populator.addScripts(demoScript);
        return populator;
    }
}
```
#### 8.验证
  * 1.启动 Application
  * 2.浏览器输入：http://localhost:8080/h2-console
  * 3.JDBC URL：jdbc:h2:mem:demo
  * 4.检查数据库里是否已有 TEST 表

#### 9.总结
使用`DataSourceInitializer`来实现 spring-boot 启动时自动执行 SQL 脚本，需要将 SQL 脚本进行改造，例如：`CREATE TABLE ...` 改成 `CREATE TABLE IF NOT EXISTS ...`，所以这个方法只能满足执行简单的 SQL 脚本的需求，如果是更复杂的 SQL 脚本，建议使用 Flyway 或者 Liquibase 。


项目代码：https://github.com/Peng-star-star/spring-study