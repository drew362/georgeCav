package com.georgeCross.george.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {
    @GetMapping("/shop")
    public String shop(Model model){
        model.addAttribute("title","Магазин");
        return "shop";
    }
}