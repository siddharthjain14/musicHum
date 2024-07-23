package com.example.cart.controller;

import com.example.cart.entity.*;
import com.example.cart.services.CartService;
import com.example.cart.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value="/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private InventoryService inventoryService;

    @PostMapping(value="/{userName}/add")
    public void addToCart(@PathVariable("userName") String userName, @RequestBody CartWrapper cartWrapper){
        cartService.addItemToCart(userName,cartService.getItemFromDB(cartWrapper.getPid(),cartWrapper.getDid(),cartWrapper.getType()));
    }

    @PostMapping(value="/{userName}/delete")
    public void deleteItemFromCart(@PathVariable("userName")String userName,@RequestBody CartWrapper cartWrapper){
        cartService.deleteItemFromCart(userName,cartService.getItemFromDB(cartWrapper.getPid(),cartWrapper.getDid(),cartWrapper.getType()));
    }

    @GetMapping(value="/{userName}/get")
    public List<CartItem> getCart(@PathVariable("userName")String userName){
        return cartService.findByUserName(userName);
    }

    @PostMapping(value="/{userName}/checkout")
    public void checkOut(@PathVariable("userName")String userName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                inventoryService.checkOut(userName);
            }
        }).start();
    }

    @GetMapping(value="/{type}/inventory/{pid}")
    public List<InventoryItem> getInventory(@PathVariable("type")String type, @PathVariable("pid")String pid){
        return inventoryService.getInventoryItem(pid,type);
    }

    @PostMapping(value="/{did}/inventory/update")
    public void updateInventory(@RequestParam("pid") String pid, @PathVariable("did")String did, @RequestBody UpdateRequestWrapper updateRequestWrapper){
        inventoryService.setProductCountAndCost(pid,did,updateRequestWrapper.getCount(),updateRequestWrapper.getCost());
    }
    @PostMapping(value="/{userName}/merge")
    ResponseEntity mergeCart(@PathVariable("userName")String userName, @RequestParam("guest_id")String guest){
        if(userName.equals("") || guest.equals("")){
            return new ResponseEntity("INVALID REQUEST",HttpStatus.BAD_REQUEST);
        }
        else{
            cartService.mergeCart(userName,guest);
            return ResponseEntity.ok().header("Content-Type","application/json").body("Yay");
        }
    }

}
