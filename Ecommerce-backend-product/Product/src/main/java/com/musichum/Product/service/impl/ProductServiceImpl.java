package com.musichum.Product.service.impl;

import com.musichum.Product.entity.Product;
import com.musichum.Product.entity.UpdateCost;
import com.musichum.Product.repository.ProductRepository;
import com.musichum.Product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product save(Product product) {
        UUID uuid = UUID.randomUUID();
        String pid = uuid.toString();
        product.setPid(pid);
        return productRepository.save(product);
    }

    @Override
    public List<Product> findByPid(String pid) {
        return productRepository.findByPid(pid);
    }

    @Override
    public void deleteByPid(String pid)

    {
        productRepository.deleteByPid(pid);
    }

    @Override
    public void rateProduct(String pid,float Rating) {
        List<Product> x = productRepository.findByPid(pid);

            float rate = x.get(0).getAvgRating();
            Rating=(x.get(0).getAvgRating()*x.get(0).getCounter()+Rating)/(x.get(0).getCounter()+1);
            x.get(0).setAvgRating(Rating);
            System.out.println( x.get(0).getAvgRating());
            x.get(0).setCounter(x.get(0).getCounter()+1);
            deleteByPid(pid);
            productRepository.save(x.get(0));
        }

    @Override
    public void updateLowestCost(UpdateCost updateCost){
        List<Product> y = productRepository.findByPid(updateCost.getPid());
        y.get(0).setLowestCost(updateCost.getLowestCost());
        y.get(0).setDid(updateCost.getLowestCostDID());
        productRepository.save(y.get(0));
    }


    @Override
    public List<Product> findAll(){
        return productRepository.findAll();
    }
}


