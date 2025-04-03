package com.georgeCross.george.controllers;

import com.georgeCross.george.models.Product;
import com.georgeCross.george.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Api("Контроллер Swagger")

public class SearchController {

    private final ProductService productService;

    @GetMapping("/search")
    @ApiOperation("поиск по номеру")
    public String searchProducts(@RequestParam(name = "query", required = false) String query, Model model) {

        List<Product> list = Collections.emptyList();

        if (StringUtils.hasText(query)) {
            list = productService.getListFindByNumberOrName(query);
            if (list.isEmpty()) {
                model.addAttribute("message", "Ничего не найдено");
            }
        }
        model.addAttribute("products", list);
        return "search";
    }
}
