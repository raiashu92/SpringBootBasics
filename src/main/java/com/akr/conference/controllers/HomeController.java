package com.akr.conference.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping
    @RequestMapping("/")
    public Map defaultHome() {
        //Jackson handles conversion (marshalling) of this map object into a json output
        Map map = new HashMap();
        map.put("app-version", appVersion);
        map.put("api call 1", "/api/v1/sessions");
        map.put("api call 2", "/api/v1/speakers");
        return map;
    }
}
