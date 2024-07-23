package com.ads.service.implementation;

import com.ads.entity.Ad;
import com.ads.repository.AdsRepository;
import com.ads.service.AdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdServiceImplementation implements AdService {
    @Autowired
    AdsRepository adsRepository;

    @Transactional
    public void addAd(Ad ad){
        System.out.println("### Inside Service addAd method");
        adsRepository.save(ad);
    }

    public void deleteById(int id){
        System.out.println("### Inside Service deleteById method");
        adsRepository.deleteById(id);
    }

    public List<Ad> findByCategory(String category){
        System.out.println("### Inside Service findByCategory method");
        String[] categories;
        if(category!=null && !category.equals("")) {
            categories = category.split(",");
            List<Ad> adsList = new ArrayList<>();
            for (String cate : categories) {
                adsList.addAll(adsRepository.findByCategory(cate));
            }
            return adsList;
        }
        else{
            return adsRepository.findByRandom();
        }
    }

    @KafkaListener(topics = "Ads",groupId = "group_id")
    public void kafkaAdd(String message){
        System.out.println("### Inside Service kafkaAdd method");
        ObjectMapper mapper=new ObjectMapper();
        Ad ad=null;
        try {
            ad=mapper.readValue(message.getBytes(),Ad.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        if(ad.getCategory()==null)
            ad.setCategory("Default");
        ad.setCategory(ad.getCategory().toLowerCase());
        adsRepository.save(ad);
    }

    public List<Ad> getAds(String userId){
        System.out.println("### Inside service getAdsforUser method");
        String result="";
        try{
            RestTemplate restTemplate=new RestTemplate();
            result=restTemplate.getForObject("http://10.177.1.164:8080/analytics/interests/"+userId,String.class);
            System.out.println("### Result "+result);
        }catch(Exception e){
            System.out.println("### Exception Message: "+e.getMessage());
            return adsRepository.findByRandom();
        }
        if(result!=null && !result.equals("")) {
            String[] array = result.toLowerCase().split(",");
            List<Ad> adsList = new ArrayList<>();
            for (int i = 0; i < array.length; i++) {
                adsList.addAll(findByCategory(array[i]));
            }
            return adsList;
        }
        else{
            return adsRepository.findByRandom();
        }
    }
}
