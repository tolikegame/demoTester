package com.example.demo.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
//套用application-h2的設定
@ActiveProfiles(profiles = "h2")
public class CustomRepositoryTest {

    @Autowired
    private CustomRepository customRepository;

    @Test
    public void testFindCustom(){
        Assert.assertEquals(customRepository.findAll().size(),4);
    }
}