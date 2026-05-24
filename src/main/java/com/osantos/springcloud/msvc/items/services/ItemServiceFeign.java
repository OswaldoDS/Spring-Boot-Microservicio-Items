package com.osantos.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osantos.springcloud.msvc.items.clients.ProductFeignClient;
import com.osantos.springcloud.msvc.items.models.Item;
import com.osantos.springcloud.msvc.items.models.Product;

@Service
public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductFeignClient client;

    @Override
    public List<Item> findAll() {

        return client.findAll().stream().map(product -> {
            Random random = new Random();
            return new Item(product, random.nextInt(10) + 1);
        }).collect(Collectors.toList());

        //// De forma simplificada
        //// Se obtienen todos los productos
        // return client.findAll()

        //// Convierte la lista en un stream para procesarla de forma funcional
        // .stream()

        //// Recorre cada producto y lo transforma en un nuevo objeto Item
        // .map(product -> new Item(product, new Random().nextInt(10)+1))

        //// Convierte el resultado nuevamente en una lista
        // .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Product product = client.details(id);
        if (product == null) {
            return Optional.empty(); // Regresa un 404
        }

        return Optional.of(
                new Item(client.details(id), new Random().nextInt(10) + 1));

    }

}
