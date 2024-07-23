package com.example.distributorProduct.controller;

import com.example.distributorProduct.entity.Distributor;
import com.example.distributorProduct.entity.DistributorLoginObject;
import com.example.distributorProduct.entity.DistributorToken;
import com.example.distributorProduct.services.DistributorService;
import com.example.distributorProduct.services.DistributorTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
@CrossOrigin
@RestController
@RequestMapping(value="/dist")
public class DistributorController {
    @Autowired
    private DistributorTokenService distributorTokenService;
    @Autowired
    private DistributorService distributorService;
    HttpHeaders httpHeaders=new HttpHeaders();
    @PostConstruct
    void set(){
        httpHeaders.set("Content-Type","application/json");
    }
    @PostMapping(value = "/add")
    ResponseEntity createDistributor(@RequestBody Distributor distributor){
        if(distributor.isEmpty()){
            return new ResponseEntity("Empty fields",HttpStatus.BAD_REQUEST);
        }
        else{
            distributorService.register(distributor);
            return new ResponseEntity("Accepted",HttpStatus.ACCEPTED);
        }
    }

    @PostMapping(value="/login")
    ResponseEntity login(@RequestBody DistributorLoginObject distributor){
        if(distributor.isEmpty()){
            return new ResponseEntity("Empty fields",HttpStatus.BAD_REQUEST);
        }
        else{
            return ResponseEntity.ok().headers(httpHeaders).body(distributorService.login(distributor));
            //("{\"token\":\""+distributorService.login(distributor)+"\"}")
        }
    }

    @GetMapping(value="/getDetails/{did}")
    ResponseEntity getDetails(@PathVariable("did") String did){
        if(did.isEmpty()){
            return new ResponseEntity("INVALID ID",HttpStatus.BAD_REQUEST);
        }
        else{
            return ResponseEntity.ok().headers(httpHeaders).body(distributorService.getDetails(did));
        }
    }

    @PostMapping(value="/update")
    ResponseEntity updateDetails(@RequestBody Distributor distributor){
        if(distributor.isEmpty()){
            return new ResponseEntity("INVALID ID",HttpStatus.BAD_REQUEST);
        }
        else{
            return ResponseEntity.ok().headers(httpHeaders).body(distributorService.updateDistributor(distributor));
        }
    }

    @PostMapping(value="/addProd/{did}/{pid}/{type}")
    ResponseEntity addProduct(@PathVariable("did")String did,@PathVariable("pid") String pid,@PathVariable("type") String type){
        if(pid.isEmpty()){
            return new ResponseEntity("INVALID ID",HttpStatus.BAD_REQUEST);
        }
        else{
            return ResponseEntity.ok().headers(httpHeaders).body(distributorService.addProduct(did,pid,type));
        }
    }
    @PostMapping(value="/removeProd/{did}/{pid}/{type}")
    ResponseEntity removeProduct(@PathVariable("did") String did,@PathVariable("pid") String pid,@PathVariable("type") String type){
        if(pid.isEmpty()){
            return new ResponseEntity("INVALID ID",HttpStatus.BAD_REQUEST);
        }
        else{
            return ResponseEntity.ok().headers(httpHeaders).body(distributorService.removeProduct(did,pid,type));
        }
    }
    @PostMapping(value="/logout/{did}")
    ResponseEntity logout(@PathVariable("did") String did){
        if(did.isEmpty()){
            return new ResponseEntity("INVALID LOGOUT",HttpStatus.BAD_REQUEST);
        }
        else{
            distributorService.logout(did);
            return new ResponseEntity("Success",HttpStatus.ACCEPTED);
        }
    }
}
