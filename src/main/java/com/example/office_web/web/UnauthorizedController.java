package com.example.office_web.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnauthorizedController {


    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "you not has Pemission";
    }
}
