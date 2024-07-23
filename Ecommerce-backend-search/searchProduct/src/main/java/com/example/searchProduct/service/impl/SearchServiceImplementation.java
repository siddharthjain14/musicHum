package com.example.searchProduct.service.impl;

import com.example.searchProduct.entity.KafkaEntity;
import com.example.searchProduct.entity.SearchItem;
import com.example.searchProduct.repository.SearchRepository;
import com.example.searchProduct.service.EdismaxQuery;
import com.example.searchProduct.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@EnableKafka
@Service
public class SearchServiceImplementation implements SearchService {
    @Autowired
    SearchRepository searchRepository;
    @Autowired
    private SolrTemplate solrTemplate;
    private String solrCoreName="searchProduct";

    @Override
    public SearchItem save(SearchItem searchItem) {
        return searchRepository.save(searchItem);
    }

    @Override
    public List<SearchItem> findBySearchString(String searchTerm) {
        if(searchTerm.isEmpty())
            return new ArrayList<SearchItem>();
        searchTerm=searchTerm.replaceAll("[^a-zA-Z0-9]"," ");
        searchTerm="*"+searchTerm+"*";
        if(searchTerm.length()>3)
            searchTerm=searchTerm+"~";
        EdismaxQuery edismaxQuery = new SimpleEdismaxQuery();
        edismaxQuery.addCriteria(new SimpleStringCriteria(searchTerm));
        edismaxQuery.setPageRequest(PageRequest.of(0, 10));
        edismaxQuery.addQueryField("title type albumName year genre artist");
        Page<SearchItem> results = solrTemplate.query(solrCoreName, edismaxQuery, SearchItem.class);
        List<SearchItem> searchResults=new ArrayList<>();
        for (SearchItem product : results) {
            searchResults.add(product);
        }
        return searchResults;
    }
    @KafkaListener(topics="SearchDelete", groupId = "${spring.kafka.consumer.group-id}")
    public void removeSearchItem(KafkaEntity kafkaEntity){
        if(searchRepository.findById(kafkaEntity.getId())!=null) {
            searchRepository.deleteById(kafkaEntity.getId());
        }
    }

    @KafkaListener(topics="SearchUpdate", groupId = "${spring.kafka.consumer.group-id}")
    public void searchUpdate(KafkaEntity kafkaEntity){
        List<SearchItem> searchItems=searchRepository.findBySearchString(kafkaEntity.getId());
        if(!searchItems.isEmpty()){
            SearchItem searchItem=searchItems.get(0);
            searchItem.setLowestCost(kafkaEntity.getCost());
            searchRepository.save(searchItem);
        }
    }

    @KafkaListener(topics="SearchAdd", groupId = "${spring.kafka.consumer.group-id}")
    public void searchAdd(KafkaEntity kafkaEntity){
        SearchItem searchItem=new SearchItem();
        searchItem.setId(kafkaEntity.getId());
        searchItem.setAlbumName(kafkaEntity.getAlbum());
        searchItem.setArtist(kafkaEntity.getArtist());
        searchItem.setGenre(kafkaEntity.getGenre());
        searchItem.setLowestCost(kafkaEntity.getCost());
        searchItem.setTitle(kafkaEntity.getTitle());
        searchItem.setType(kafkaEntity.getType());
        searchItem.setYear(kafkaEntity.getYear());
        searchRepository.save(searchItem);
    }

}
