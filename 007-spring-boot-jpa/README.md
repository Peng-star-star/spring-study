# Spring Boot 集成 Spring Data JPA

#### 1.简述目的

+ Spring Boot 集成 Spring Data JPA
+ Spring Data JPA 简单查询
+ 根据实体自动创建表

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
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
</dependencies>
```

#### 5.修改 application.properties

```
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:file:./target/foobar
spring.datasource.username=sa
spring.jpa.hibernate.ddl-auto=create
``` 

#### 6.创建实体 Customer.class

* 创建文件 `com.example.demo.domain.Customer`

```SQL
package com.example.demo.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    public Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
```


#### 7.创建 CustomerRepository.class


* 创建文件 `com.example.demo.mapper.CustomerRepository`

```java
package com.example.demo.mapper;

import com.example.demo.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
}
```

Spring Boot 集成 Spring Data JPA 已完成，下面编写测试用例来进行查询

#### 8.测试用例

* 创建文件 `com.example.demo.mapper.CustomerRepositoryTest`

```java
package com.example.demo.mapper;

import com.example.demo.domain.Customer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Order(1)
    public void init() {
        customerRepository.save(new Customer("Jack", "Bauer"));
        customerRepository.save(new Customer("Chloe", "O'Brian"));
    }

    @Test
    public void findByLastName() {
        List<Customer> customers = customerRepository.findByLastName("Bauer");
        assertThat(customers.size()).isEqualTo(1);
    }

    @Test
    public void findById() {
        Customer customer = customerRepository.findById(1L);
        assertThat(customer.getFirstName()).isEqualTo("Jack");
    }
}
```

以上代码可以在可以在 [GitHub仓库](https://github.com/Peng-star-star/spring-study/tree/main/007-spring-boot-jpa) 找到

#### 10.参考

[官网 - Accessing Data with JPA ](https://spring.io/guides/gs/accessing-data-jpa/) （官网示例 Spring Boot 为 3.0.0，与本文档例子稍有不同）
