package com.example.demo.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.repository.model.User;



@SpringBootTest
@RunWith(SpringRunner.class)
public class UserPagingRepositoryTest {

    @Autowired
    UserPagingRepository userPagingRepository;

    // 分页
    @Test
    public void page() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<User> page = userPagingRepository.findAll(pageable);
        Assert.assertEquals(5, page.getSize());
        Assert.assertEquals(0, page.getNumber());
    }

    // 排序
    @Test
    public void order() {
        User user = new User();
        user.setName("z");
        user.setLastname("z");
        userPagingRepository.save(user);
        Sort sort = Sort.by(Direction.DESC, "name");
        Iterable<User> iterable = userPagingRepository.findAll(sort);
        int i = 0;
        for (User u : iterable) {
            if (i == 0) {
                Assert.assertEquals("z", u.getName());
                break;
            }
        }
    }
    
    // 通过方法名查询
    @Test
    public void findByLastname() {
        List<User> users = userPagingRepository.findByLastname("lastname");
        Assert.assertNotNull(users);
    }

    // 通过方法名查询
    @Test
    public void findByLastname2() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<User> page = userPagingRepository.findByLastname("lastname", pageable);
        Assert.assertEquals(5, page.getSize());
        Assert.assertEquals(0, page.getNumber());
    }

    // 使用@Query
    @Test
    public void annotation() {
        List<User> users = userPagingRepository.findByAnnotation("lastname");
        Assert.assertNotNull(users);
    }
}
