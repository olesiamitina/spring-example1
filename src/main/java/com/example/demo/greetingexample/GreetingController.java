package com.example.demo.greetingexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    @Value("${greeting.id}")
    private int cCount;
    @Value("${greeting.content}")
    private String cContent;

    @Autowired
    AppPropertiesConfiguration appPropertiesConfiguration;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @PostMapping("/greeting/save")
    public String saveName(@RequestBody Greeting greeting) {
        return "Saved" + greeting.getContent();
    }

    @GetMapping("/defaultGreeting")
    public Greeting defaultGreeting() {
        return new Greeting(cCount, cContent);
    }

    @GetMapping("/propertiesConfiguration")
    public List<Object> getConfigurationProperties() {
        List<Object> tempProperties = new ArrayList<>();
        tempProperties.add(appPropertiesConfiguration.getId());
        tempProperties.add(appPropertiesConfiguration.getContent());
        return tempProperties;
    }
}
