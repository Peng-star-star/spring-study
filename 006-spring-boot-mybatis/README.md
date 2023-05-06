# Spring Boot 集成 MyBatis

#### 1.简述目的

+ Spring Boot 集成 MyBatis
+ MyBatis 使用
    - 使用注解查询
    - 使用XML查询
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
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
  </dependency>
  <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
  </dependency>
  <dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.3.0</version>
  </dependency>
</dependencies>
```

#### 5.修改 application.properties

```
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:file:./target/foobar
spring.datasource.username=sa
spring.sql.init.mode=always
mybatis.mapper-locations=classpath:mapper/*.xml
``` 

#### 6.创建 SQL 脚本

* 创建文件 `src/main/resources/schema.sql`

```SQL
drop table if exists city;
create table city (id int primary key auto_increment, name varchar, state varchar, country varchar);
```

* 创建文件 `src/main/resources/data.sql`

```SQL
insert into city (name, state, country) values ('San Francisco', 'CA', 'US');
```

#### 7.创建 City.class

* 创建文件 `com.example.demo.domain.City`

```java
package com.example.demo.domain;

import lombok.Data;

@Data
public class City {

    private Long id;
    private String name;
    private String state;
    private String country;
}
```

#### 8.创建 CityMapper.class


* 使用注解查询，编写以下代码

```java
@Select("SELECT * FROM CITY WHERE state = #{state}")
City findByState(@Param("state") String state);
```

* 使用XML查询，编写以下代码

```java
City selectCityById(@Param("id") int id);
```


#### 9.编写 XML 文件

* 创建文件夹 `src/main/resources/mapper`

* 创建文件 `src/main/resources/mapper/schema.sql`

```XML
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.demo.mapper.CityMapper">
    <select id="selectCityById" resultType="com.example.demo.domain.City">
        select id, name, state, country from city where id = #{id}
    </select>
</mapper>
```

Spring Boot 集成 MyBatis 已完成，下面编写测试用例来进行查询

#### 10.测试用例

* 创建包名 `com.example.demo.mapper`
* 创建文件 `com.example.demo.mapper.CityMapperTest`

```java
@SpringBootTest
public class CityMapperTest {

    @Autowired
    CityMapper cityMapper;

    @Test
    public void findByState() {
        City city = cityMapper.findByState("CA");
        assertThat(city.getId()).isEqualTo(1);
    }

    @Test
    public void selectCityById() {
        City city = cityMapper.selectCityById(1);
        assertThat(city.getState()).isEqualTo("CA");
    }
}
```

以上代码可以在可以在 [GitHub仓库](https://github.com/Peng-star-star/spring-study/tree/main/006-spring-boot-mybatis) 找到

#### 11.参考

[官网代码库 - MyBatis-Spring-Boot-Starter 简介](https://github.com/mybatis/spring-boot-starter/blob/master/mybatis-spring-boot-autoconfigure/src/site/zh/markdown/index.md)
