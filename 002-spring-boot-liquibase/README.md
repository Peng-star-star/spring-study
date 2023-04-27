#### 1.简述目的
本教程可以学到：    
* 在 Spring Boot 中集成 Liquibase API
* 配置 Liquibase 连接到数据库
* 编写和执行数据库更新

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
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```

#### 5.修改 application.properties
```
spring.h2.console.enabled=true
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:file:./target/foobar
spring.datasource.username=sa
spring.liquibase.change-log=classpath:db/changelog/changelog.sql
``` 
#### 6.创建第一个迁移
 * 创建文件夹 `src/main/resources/db/changelog`
 * 创建文件 `src/main/resources/db/changelog/changelog.sql`
```SQL
--liquibase formatted sql

--changeset your.name:1 labels:example-label context:example-context
--comment: example comment
create table person (
    id int primary key auto_increment not null,
    name varchar(50) not null,
    address1 varchar(50),
    address2 varchar(50),
    city varchar(30)
)
--rollback DROP TABLE person;
```

#### 7.执行程序
 * 执行 Application.java
 * 访问 http://localhost:8080/h2-console
 * JDBC URL 输入 `jdbc:h2:file:./target/foobar` 
 
  通过查看数据库，出现 PERSON 表，得知第一个更新已经执行。

#### 8.编写第二个迁移
  修改文件 `src/main/resources/db/changelog/changelog.sql` 添加下面代码
```SQL

--changeset your.name:2 labels:example-label context:example-context
--comment: example comment
create table company (
    id int primary key auto_increment not null,
    name varchar(50) not null,
    address1 varchar(50),
    address2 varchar(50),
    city varchar(30)
)
--rollback DROP TABLE company;
```
  
#### 9.执行程序
 * 执行 Application.java
 * 访问 http://localhost:8080/h2-console  
 
  通过查看 COMPANY 表里的数据，得知第二个更新已经执行。


#### 10.参考
 [官网 - Using Liquibase with Spring Boot and Maven Project](https://docs.liquibase.com/tools-integrations/springboot/using-springboot-with-maven.html)