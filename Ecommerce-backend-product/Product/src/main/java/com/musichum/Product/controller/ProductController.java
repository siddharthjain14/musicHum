package com.musichum.Product.controller;

import com.musichum.Product.entity.Product;
import com.musichum.Product.entity.RatingEntity;
import com.musichum.Product.entity.UpdateCost;
import com.musichum.Product.service.ProductService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value="/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping(value = "/add")
    public ResponseEntity save(@RequestBody Product product)
    {if(product.getAlbum()==null || product.getArtist()==null|| product.getCoverUrl()==null||product.getDownloads()==0||product.getGenre()==null||product.getMood()==null||product.getTitle()==null||product.getYear()==0)
        return new ResponseEntity("Incomplete Details", HttpStatus.BAD_REQUEST);
    else
    return ResponseEntity.ok()
            .body(productService.save(product));
    }
    @GetMapping(value = "/{pid}")
    ResponseEntity findByPid(@PathVariable("pid") String pid){
        if(productService.findByPid(pid).size()==0)
            return new ResponseEntity("NOT FOUND", HttpStatus.NOT_FOUND);
        else
        return ResponseEntity.ok()
                .body(productService.findByPid(pid));}

   @DeleteMapping(value = "/{pid}/delete")
   public ResponseEntity deleteByPid(@PathVariable("pid") String pid)
   {    if(productService.findByPid(pid).size()==0)
       return new ResponseEntity("NOT FOUND", HttpStatus.NOT_FOUND);
    else
   {productService.deleteByPid(pid);
       return ResponseEntity.ok()
               .body("Deleted");}
   }

    @PostMapping(value = "/rate")
    ResponseEntity rateProduct(@RequestBody RatingEntity ratingEntity) {
        if (ratingEntity.getPid()==null||ratingEntity.getRating()==0.0)
            return new ResponseEntity("Incomplete Details", HttpStatus.BAD_REQUEST);
        else { productService.rateProduct( ratingEntity.getPid(), ratingEntity.getRating());
                return ResponseEntity.ok()
                .body("Successful");
                    }
    }

    @GetMapping(value = "/all")
    List<Product> getAllProducts(){
        return productService.findAll();
    }
}
