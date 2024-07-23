package com.ads.controller;

import com.ads.entity.Ad;
import com.ads.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ad")
public class AdsController {
    @Autowired
    AdService adService;

    @GetMapping("/{category}")
    public List<Ad> getAds(@PathVariable String category){
        System.out.println("### Inside controller getAds method");
        return adService.findByCategory(category);
    }

    @PostMapping("/make")
    public void addAd(@RequestBody Ad ad){
        System.out.println("### Inside controller addAd method");
        adService.addAd(ad);
    }

    @GetMapping("/user/{userId}")
    public List<Ad> getUserAds(@PathVariable("userId") String userId){
        System.out.println("### Inside controller getUserAds method");
        return adService.getAds(userId);
    }

}
