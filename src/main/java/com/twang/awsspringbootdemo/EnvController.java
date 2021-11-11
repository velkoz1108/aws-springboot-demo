package com.twang.awsspringbootdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class EnvController {
    @GetMapping("/")
    public String home() {
        return "It works";
    }

    @GetMapping("/{envName}")
    public String index(@PathVariable String envName) {
        String property = System.getProperty(envName);
        String env = System.getenv(envName);
        return "{\"property\":\"" + property + "\",\"env\":\"" + env + "\"}";
    }
}
