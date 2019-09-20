package com.sensetime.resttemplate.controller;

import lombok.extern.slf4j.Slf4j;

import com.sensetime.resttemplate.common.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") Integer id) {
        User u1 = new User();

        u1.setId(id);
        u1.setName("htd");
        u1.setAge(20);

        return u1;

    }

    @RequestMapping(value = "/object/user",method = RequestMethod.GET)
    public User getRestUser() {
        //参数1为请求的url
        //参数2为返回值解析成哪个类
        //参数3为传给url的参数
        User user = restTemplate.getForObject("http://localhost:8080/api/v1/user/{id}",
                User.class, 1);

        return user;
    }

    @RequestMapping(value = "/entity/user",method = RequestMethod.GET)
    public User getEntityUser() {
        ResponseEntity<User> responseEntity =
                restTemplate.getForEntity("http://localhost:8080/api/v1/user/{id}",
                        User.class,100);

        System.out.println(responseEntity.toString());

        System.out.println(responseEntity.getStatusCode());

        return responseEntity.getBody();

    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User postUser(@RequestBody User user) {

        return user;
    }

    @RequestMapping(value = "/object/user", method = RequestMethod.POST)
    public User postObjectUser(@RequestBody User user) {

        //参数1为url地址
        //参数2为post的参数
        //参数3为解析成哪个类
        User user1 = restTemplate.postForObject("http://localhost:8080/api/v1/user",
                user, User.class);

        return user1;
    }

    @RequestMapping(value = "/entity/user", method = RequestMethod.POST)
    public User postEntityUser(@RequestBody User user) {

        ResponseEntity<User> responseEntity =
                restTemplate.postForEntity("http://localhost:8080/api/v1/user",
                        user, User.class);

        return responseEntity.getBody();
    }


    @RequestMapping(value = "/sslcert", method = RequestMethod.GET)
    public String skipSSLCert() {

        String url = "https://node-01.hadoop-video.data.sensetime.com:50470/webhdfs/v1/user/hatieda?op=getcontentsummary";

        String result = restTemplate.getForObject(url, String.class);

        return result;
    }

}
