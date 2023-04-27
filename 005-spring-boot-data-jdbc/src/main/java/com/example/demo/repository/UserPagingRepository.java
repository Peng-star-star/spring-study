package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.repository.model.User;

public interface UserPagingRepository extends PagingAndSortingRepository<User, Long>{
    
    /**
     * 8.4.2. Query Creation </br>
     * https://docs.spring.io/spring-data/jdbc/docs/2.4.11/reference/html
     * @param lastname
     * @return
     */
    List<User> findByLastname(String lastname);
    
    /**
     * 8.4.4. Special parameter handling </br>
     * https://docs.spring.io/spring-data/jdbc/docs/2.4.11/reference/html
     * @param lastname
     * @return
     */
    Page<User> findByLastname(String lastname, Pageable pageable);
    
    @Query("select id, name,lastname from t_user u where u.lastname = :lastname")
    List<User> findByAnnotation(String lastname);
}
