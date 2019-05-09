package com.susd.controllers;

import com.susd.domain.complex.DictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="express")
@Controller
public class ExpressController {

    @Autowired
    private DictRepository dictRepository;

    public String index(){
        return "express/index";
    }



}
