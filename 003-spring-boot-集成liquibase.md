# spring-boot-集成liquibase

#### 1.前言
上次使用 flyway 实现数据库从任意版本升级到最新版本，还有一个与 flyway 功能相近的库 liquibase，现在使用 liquibase 来实现。
  
#### 2.目的
  使用 liquibase 实现自动执行 SQL 脚本，并且可以从任意版本升级到最新版本。

#### 3.创建项目
  * 访问：https://start.spring.io/
  * Project 选择 Maven
  * Language 选择 Java
  * Spring Boot 选择 2.7.15
  * Artifact 填写 003-spring-boot-liquibase
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
    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
    </dependency>
</dependencies>
```
#### 5.修改 application.properties
```
spring.h2.console.enabled=true
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:demo
spring.datasource.username=sa
spring.liquibase.change-log=classpath:db/changelog/changelog.sql
``` 

#### 6.编写脚本
在 `src/main/resources/db/changelog` 创建 changelog.sql 文件，内容如下：
```SQL
-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE test_table
(
    test_id     INT,
    test_column VARCHAR,
    PRIMARY KEY (test_id)
)
```

#### 7.验证
  * 1.启动 Application
  * 2.浏览器输入：http://localhost:8080/h2-console
  * 3.JDBC URL：jdbc:h2:mem:demo
  * 4.检查数据库里是否已有 test_table 表

#### 8.总结
集成 liquibase 也比较简单，1.需要 pom.xml 引入 flyway 依赖；2.application.properties 设置启动脚本；3.编写脚本。
相比 flyway 它的脚本必须加一下注释，例如：`-- changeset liquibase:1`。如果实现自动执行 SQL 脚本，并且可以从任意版本升级到最新版本，选择 flyway 就可以了。
拜拜，摸鱼去了。

#### 9.参考
  * [官网 - Using Liquibase with Spring Boot and Maven Project](https://contribute.liquibase.com/extensions-integrations/directory/integration-docs/springboot/using-springboot-with-maven/)
  * [官网 - Migrating with SQL](https://docs.liquibase.com/workflows/liquibase-community/migrate-with-sql.html)

项目代码：https://github.com/Peng-star-star/spring-study