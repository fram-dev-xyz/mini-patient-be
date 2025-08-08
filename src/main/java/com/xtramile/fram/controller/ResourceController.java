package com.xtramile.fram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    @GetMapping("/states")
    public String getStates(){
        return "OK";
    }

    @GetMapping("/suburbs")
    public String getSuburbs(){
        return "OK";
    }
}
