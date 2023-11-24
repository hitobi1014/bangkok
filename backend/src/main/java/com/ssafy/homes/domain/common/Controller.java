package com.ssafy.homes.domain.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/test")
    public String test(){
        System.out.println("Controller.test");
        return "Test world";
    }
}
