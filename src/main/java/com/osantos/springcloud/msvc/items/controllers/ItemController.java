package com.osantos.springcloud.msvc.items.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.osantos.springcloud.msvc.items.models.Item;
import com.osantos.springcloud.msvc.items.services.ItemService;

@RestController
public class ItemController {

    //Se inyecta dependencia por constructor
    final private ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<Item> list (){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> details (@PathVariable Long id) {
        Optional<Item> itemOptional = service.findById(id);
        if(itemOptional.isPresent()){
            return ResponseEntity.ok(itemOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

}
