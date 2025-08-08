package com.xtramile.fram.controller;

import com.xtramile.fram.model.dto.StateDto;
import com.xtramile.fram.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping("/states")
    public List<StateDto> getStates(){
        return resourceService.getStates();
    }

    @GetMapping("/states/{id}")
    public StateDto getState(@PathVariable int id){
        return resourceService.getState(id);
    }
}
