package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    // http://127:0.0.1:8080/ROOT/
    @RequestMapping(value = { "/", "home" }, method = RequestMethod.GET)
    public String home(Model model) {

        return "home";
    }

    @RequestMapping(value = "/page403", method = RequestMethod.GET)
    public String home() {
        return "page403";
    }

}
