package com.suraj.realestate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping
    public String test(){
        return "This is a test message from the test controller";
    }

    @GetMapping("/test2")
    public String test2(){
        return "This is a test2 message from the test controller";
    }

}
