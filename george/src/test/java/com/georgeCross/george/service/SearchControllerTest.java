package com.georgeCross.george.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.georgeCross.george.controllers.SearchController;
import com.georgeCross.george.models.Georg;
import com.georgeCross.george.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeorgService georgService;

    @MockBean
    ProductRepository productRepository;

    private Georg mockGeorg1;
    private Georg mockGeorg2;

    @BeforeEach
    void setUp() {

        mockGeorg1 = new Georg();
        mockGeorg1.setId(1L);
        mockGeorg1.setDescription("privet");
        mockGeorg1.setName("Ivan");
        mockGeorg1.setNumber(456234);

        mockGeorg2 = new Georg();
        mockGeorg2.setId(1L);
        mockGeorg2.setDescription("poka");
        mockGeorg2.setName("Petr");
        mockGeorg2.setNumber(456984);
    }

    @Test
    @DisplayName("Успешный поиск Георгиевских крестов по номеру или фамилии")
    void goodSearch() throws Exception {
        String queryText = "Petr";
        List<Georg> expectedList = List.of(mockGeorg2);

        when(georgService.getListFindByNumberOrName(queryText)).thenReturn(expectedList);
        mockMvc.perform(get("/api/search")
                        .param("query", queryText)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].number").value("456984"))
                .andExpect(jsonPath("$[0].name").value("Petr"))
                .andExpect(jsonPath("$[0].description").value("poka"));
    }

    @Test
    @DisplayName("Не найдено")
    public void noGood() throws Exception {
        String searchQuery = "Такого нет";

        when(georgService.getListFindByNumberOrName(searchQuery)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/search")
                        .param("query", searchQuery)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}