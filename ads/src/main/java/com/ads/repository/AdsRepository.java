package com.ads.repository;

import com.ads.entity.Ad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdsRepository extends CrudRepository<Ad,Integer> {
    List<Ad> findByCategory(String category);
    @Query(nativeQuery=true, value="SELECT *  FROM ad ORDER BY random() LIMIT 5")
    List<Ad> findByRandom();
}
