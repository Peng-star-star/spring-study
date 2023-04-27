#### 1.简述目的
本教程可以学到：    
* 在 Spring Boot 中集成 Flyway API
* 配置 Flyway 连接到数据库
* 编写和执行数据库迁移。

#### 2.前提
 * Java 8, 9, 10, 11 or 12
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
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
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
``` 
#### 6.创建第一个迁移
 * 创建文件夹 `src/main/resources/db/migration`
 * 创建文件 `src/main/resources/db/migration/V1__Create_person_table.sql`
```SQL
create table PERSON (
  ID int not null,
  NAME varchar(100) not null
);
```

#### 7.执行程序
 * 执行 Application.java
 * 访问 http://localhost:8080/h2-console
 * JDBC URL 输入 `jdbc:h2:file:./target/foobar` 
 
  通过查看数据库，出现 PERSON 表，得知第一个迁移已经执行。

#### 8.创建第二个迁移
  创建文件 `src/main/resources/db/migration/V2__Add_people.sql`
```SQL
insert into PERSON (ID, NAME) values (1, 'Axel');
insert into PERSON (ID, NAME) values (2, 'Mr. Foo');
insert into PERSON (ID, NAME) values (3, 'Ms. Bar');
```
  
#### 9.执行程序
 * 执行 Application.java
 * 访问 http://localhost:8080/h2-console  
 
  通过查看 PERSON 表里的数据，得知第二个迁移已经执行。

#### 10.参考
 [官网 - First Steps - API](https://documentation.red-gate.com/fd/first-steps-api-184127575.html)