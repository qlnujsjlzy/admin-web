package com.angular.admin.controller;

import com.angular.admin.domain.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("")
public class HelloWorldController {

    private static final String template = "Hello spring boot, %s!";
    private final AtomicLong counter = new AtomicLong();
    @RequestMapping(value ="" ,method=RequestMethod.GET)
    public String sayHelloStr(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
        return "hello world ";
    }
    @RequestMapping(value ="hello" ,method=RequestMethod.GET)
    public Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}
