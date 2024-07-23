package com.example.searchProduct.service;

import com.example.searchProduct.entity.SearchItem;

import java.util.List;

public interface SearchService {
    SearchItem save(SearchItem searchItem);
    List<SearchItem> findBySearchString(String searchTerm);
}