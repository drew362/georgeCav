package com.georgeCross.george.service;

import com.georgeCross.george.models.Georg;
import com.georgeCross.george.repositories.GeorgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeorgService {

    private final GeorgRepository productRepository;

    public List<Georg> getListFindByNumberOrName(String query) {
        return productRepository.findByNumberOrName(query);
    }

}
