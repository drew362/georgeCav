package com.georgeCross.george.controllers;

import com.georgeCross.george.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Api("Контроллер Swagger")

public class SearchController {

    private final ProductService productService;

    @GetMapping("/search")
    @ApiOperation("поиск по номеру")
    public String searchProducts(
            @RequestParam(name = "number", required = false) String number,
            @RequestParam(name = "name", required = false) String name,
            Model model) {

        if (number != null && !number.isEmpty()) {
            model.addAttribute("products", productService.getListFindByNumber(number));
        } else if (name != null && name.isEmpty()) {
            model.addAttribute("products", productService.getListFindByName(name));
        }
        return "search";
    }
}
