package com.ads.service;

import com.ads.entity.Ad;

import java.util.List;

public interface AdService {
    List<Ad> findByCategory(String category);
    void addAd(Ad ad);
    void deleteById(int id);
    List<Ad> getAds(String userId);
}
