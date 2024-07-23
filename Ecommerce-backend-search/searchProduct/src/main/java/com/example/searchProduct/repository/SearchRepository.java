package com.example.searchProduct.repository;

import com.example.searchProduct.entity.SearchItem;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends SolrCrudRepository<SearchItem,String> {
    @Query("?0")
    List<SearchItem> findBySearchString(String searchTerm);

}
