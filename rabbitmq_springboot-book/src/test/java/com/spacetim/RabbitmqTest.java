package com.spacetim; /**
 * Created by Administrator on 2019/3/30.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacetim.entity.Person;
import com.spacetim.rabbitmq_basic.BasicPublisher;
import com.spacetim.testRabbitmq.BasicPublisherTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author:debug (SteadyJack)
 * @Date: 2019/3/30 23:40
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitmqTest {

    private static final Logger log= LoggerFactory.getLogger(RabbitmqTest.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BasicPublisher basicPublisher;

    @Autowired
    private BasicPublisherTest basicPublisherTest;

    @Test
    public void test1() throws Exception{
        String msg="hello world!~~~";
//        String s = objectMapper.writeValueAsString(msg);
        basicPublisher.sendMsg(msg);
    }

    @Test
    public void test2(){
        basicPublisherTest.sendMsg("~~~~~~~成功");
    }

    @Test
    public void test3(){
        basicPublisher.sendObjectMsg(new Person(18,"spacetim","father"));
    }



}































