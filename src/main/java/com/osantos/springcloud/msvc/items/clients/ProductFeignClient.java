package com.osantos.springcloud.msvc.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.osantos.springcloud.msvc.items.models.Product;

//Clase para consumir el API REST
@FeignClient(name = "msvc-products", path = "/api/v1/products") // Se especifica el puerto del proyecto Products
public interface ProductFeignClient {

    @GetMapping
    List<Product> findAll();

    // Tiene que se el mismo nombre de rutas para poder acceder y los mismo
    // parámetros y el mismo tipo de retorno
    @GetMapping("/{id}")
    Product details(@PathVariable Long id);

}
