package com.osantos.springcloud.msvc.items.controllers;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osantos.springcloud.msvc.items.models.Item;
import com.osantos.springcloud.msvc.items.services.ItemService;

@RestController
@RequestMapping("/api/v1/products")
public class ItemController {

    //Se inyecta dependencia por constructor
    final private ItemService service;

    /**
     * Se puede sustituir el @Primary de la clase ItemServiceWebClient
     * por ItemController(@Qualifier("itemServiceWebClient") ItemService service)
     * para indicar que es la clase ItemServiceWebClient será la principal
     * Es otra manera de poder definirlo
     */ 
    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<Item> list (){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details (@PathVariable Long id) {
        Optional<Item> itemOptional = service.findById(id);
        if(itemOptional.isPresent()){
            return ResponseEntity.ok(itemOptional.get());
        }

        // return ResponseEntity.notFound().build();
        return ResponseEntity.status(404)
        .body(Collections.singletonMap("message", "No existe el producto en el microservicio msvc-products")); 
        //Para devolver un 404 con mensaje
    }

}
