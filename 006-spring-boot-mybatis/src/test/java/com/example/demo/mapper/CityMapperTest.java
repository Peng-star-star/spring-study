package com.example.demo.mapper;

import com.example.demo.domain.City;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
