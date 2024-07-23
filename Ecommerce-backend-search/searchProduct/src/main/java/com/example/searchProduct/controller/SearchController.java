package com.example.searchProduct.controller;

import com.example.searchProduct.entity.SearchItem;
import com.example.searchProduct.service.impl.SearchServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value="/search")
public class SearchController {
    @Autowired
    SearchServiceImplementation searchServiceImplementation;
    @PostMapping(value="/{string}")
    public List<SearchItem> findByFirstName(@PathVariable("string") String searchTerm){
        return searchServiceImplementation.findBySearchString(searchTerm);
    }
    @PostMapping(value="/")
    public ResponseEntity throwError(){
        return new ResponseEntity("No Results Found",HttpStatus.BAD_REQUEST);
    }
}
