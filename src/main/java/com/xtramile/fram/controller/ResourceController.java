package com.xtramile.fram.controller;

import com.xtramile.fram.model.dto.StateDto;
import com.xtramile.fram.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
