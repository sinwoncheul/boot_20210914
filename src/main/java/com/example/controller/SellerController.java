package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "seller_home";
    }

}
