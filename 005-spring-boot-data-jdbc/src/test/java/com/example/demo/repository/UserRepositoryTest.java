package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.repository.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    // 新增
    @Test
    public void insert() {
        User user = new User();
        user.setName("test insert");
        user.setLastname("test insert");
        User saveUser = userRepository.save(user);
        log.info(saveUser.toString());
    }

    // 修改
    @Test
    public void update() {
        User user = new User();
        user.setId(-3L);
        user.setName("update");
        user.setLastname("update");
        User saveUser = userRepository.save(user);
        log.info(saveUser.toString());
    }

    // 查询
    @Test
    public void query() {
        Optional<User> optional = userRepository.findById(1L);
        log.info(optional.get().toString());
    }

    // 删除
    @Test
    public void delete() {
        Assert.assertTrue(userRepository.existsById(-2L));
        userRepository.deleteById(-2L);
        Assert.assertFalse(userRepository.existsById(-2L));
    }
    
    // 批量新增
    @Test
    public void batchInsert() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setName("batch insert 1");
        user.setLastname("batch insert 1");
        users.add(user);
        User user2 = new User();
        user2.setName("batch insert 2");
        user2.setLastname("batch insert 2");
        users.add(user2);
        Iterable<User> iterable = userRepository.saveAll(users);
        for (User u : iterable) {
            log.info(u.toString());
        }
    }
    
    // 批量删除
    @Test
    public void batchDelete() {
        Assert.assertTrue(userRepository.existsById(-1L));
        List<Long> ids = new ArrayList<Long>();
        ids.add(-1L);
        userRepository.deleteAllById(ids);
        Assert.assertFalse(userRepository.existsById(-1L));
    }
}
