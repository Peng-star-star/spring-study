# spring-boot-集成flyway

#### 1.前言
上次使用`DataSourceInitializer`来实现 spring-boot 启动时自动执行SQL脚本，测试同事对这个功能很满意，于是我又摸鱼几天。
但是今天正摸着鱼，测试同事又提了新需求，在自动执行SQL脚本基础上，要求程序可以从任意版本升级到最新版本。由于`DataSourceInitializer`方式没办法做到数据库版本控制，所以需要寻找新的解决方案。
  
#### 2.目的
  使用 flyway 实现数据库自动执行 SQL 脚本，并且可以从任意版本升级到最新版本。

#### 3.创建项目
  * 访问：https://start.spring.io/
  * Project 选择 Maven
  * Language 选择 Java
  * Spring Boot 选择 2.7.15
  * Artifact 填写 002-spring-boot-flyway
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
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
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
在 `src/main/resources/db/migration` 创建 V1__Create_person_table.sql 文件，内容如下：
```SQL
create table PERSON (
  ID int not null,
  NAME varchar(100) not null
);
```
在 `src/main/resources/db/migration` 创建 V2__Add_people.sql 文件，内容如下：
```SQL
insert into PERSON (ID, NAME) values (1, 'Axel');
insert into PERSON (ID, NAME) values (2, 'Mr. Foo');
insert into PERSON (ID, NAME) values (3, 'Ms. Bar');
```

#### 7.验证
  * 1.启动 Application
  * 2.浏览器输入：http://localhost:8080/h2-console
  * 3.JDBC URL：jdbc:h2:mem:demo
  * 4.检查数据库里是否已有 PERSON 表和表里的数据

#### 8.总结
  集成 flyway 比较简单，只需要 pom.xml 引入 flyway 依赖，然后就可以愉快的编写 SQL 脚本了，当然了它对 SQL 脚本的命名有一定要求，可以查看[官网 - Migrations](https://documentation.red-gate.com/fd/migrations-184127470.html#versioned-migrations) 。拜拜，摸鱼去了！

#### 9.参考
  * [官网 - Why database migrations](https://documentation.red-gate.com/fd/why-database-migrations-184127574.html)
  * [官网 - Quickstart - API](https://documentation.red-gate.com/fd/quickstart-api-184127575.html)
  * [官网 - Migrations](https://documentation.red-gate.com/fd/migrations-184127470.html#versioned-migrations)

项目代码：https://github.com/Peng-star-star/spring-study