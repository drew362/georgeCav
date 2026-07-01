package com.georgeCross.george.controllers;

import com.georgeCross.george.models.Georg;
import com.georgeCross.george.service.GeorgService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class SearchController {

    private final GeorgService georgService;

    @GetMapping("/search")
    public List<Georg> searchHeroes(@RequestParam("query")String queryText){
        return georgService.getListFindByNumberOrName(queryText);
    }

}
