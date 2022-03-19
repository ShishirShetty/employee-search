package com.example.demoApp.controller;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    public static int count = 0;

    @RequestMapping(path = "/sayHello", method = RequestMethod.GET)
    public String sayHello(@RequestParam("fName") String fullName,
                           @RequestParam("lName") String lastName) {
        return "Namaste " + fullName + lastName;
    }

    @RequestMapping(path = "/counter", method = RequestMethod.GET)
    public Integer counter() {
        return count++;
    }

}
