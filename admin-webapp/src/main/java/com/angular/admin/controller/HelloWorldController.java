package com.angular.admin.controller;

import com.angular.admin.domain.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/")
@CrossOrigin
public class HelloWorldController {

    private static final String template = "Hello spring boot, %s!";
    private final AtomicLong counter = new AtomicLong();


    @RequestMapping(value = "greeting", method = RequestMethod.GET)
    public List<Greeting> sayHello(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
        Greeting g1 = new Greeting(counter.incrementAndGet(), String.format(template, name));
        Greeting g2 = new Greeting(counter.incrementAndGet(), String.format(template, name));
        Greeting g3 = new Greeting(counter.incrementAndGet(), String.format(template, name));
        List<Greeting> list = new ArrayList<>();
        list.add(g1);
        list.add(g2);
        list.add(g3);
        return list;
    }

}
