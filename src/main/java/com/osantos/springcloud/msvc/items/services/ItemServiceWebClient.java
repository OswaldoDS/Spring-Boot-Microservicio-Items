package com.osantos.springcloud.msvc.items.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.osantos.springcloud.msvc.items.models.Item;
import com.osantos.springcloud.msvc.items.models.Product;

@Primary // Indicando que este servicio será el principal de ItemService
@Service
public class ItemServiceWebClient implements ItemService {

    private final WebClient.Builder client;

    public ItemServiceWebClient(Builder client) {
        this.client = client;
    }

    @Override
    public List<Item> findAll() {

        // Se usa un cliente HTTP reactivo -> WebClient para llamar a otro microservicio

        // Se construye una instancia del cliente HTTP
        return this.client.build()
                // Indica que la peticiíon HTTP será de tipo GET
                .get()
                // Se define la URL del servicio al que se hará la petición

                // Nota: en Spring Cloud LoadBalancer / Eureka, solo representa el nombre lógico del servicio, el path del endpoint debes ponerlo tú.

                // .uri("http://msvc-products/api/v1/products")
                // Le dice al servidor que se espera recibir datos en formato JSON
                .accept(MediaType.APPLICATION_JSON)
                // Ejecuta la petición y se prepara la respuesta para leerla
                .retrieve()
                // Convierte el body de la respuesta en un flujo FLUX de objetos Item
                .bodyToFlux(Product.class)

                .map(product -> new Item(product, new Random().nextInt(10) + 1))

                // Convierte ese flujo de muchos Items en una List<Item>
                .collectList()
                // Espera de forma síncrona hasta recibir la respuesta completa y devuelve la
                // lista final.
                .block();
    }

    @Override
    public Optional<Item> findById(Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        try {
            // Envuelve el resultado en un Optional, si el resultado es null, devuelve
            // Optional.empty()

            return Optional.of(
                    // Construye una instancia del cliente HTTP
                    this.client.build()
                            // Indica que se hará una petición HTTP GET
                            .get()
                            // Define la URL del servicio {id} se reemplaza con el valor guardado en params
                            // Nota: en Spring Cloud LoadBalancer / Eureka, solo representa el nombre lógico del servicio, el path del endpoint debes ponerlo tú.

                            // .uri("http://msvc-products/api/v1/products/{id}", params)
                            .uri("/{id}", params)
                            // Le dice al servidor que espera recibir JSON
                            .accept(MediaType.APPLICATION_JSON)
                            // Ejecuta la petición y obtiene la respuesta
                            .retrieve()
                            // Convierte el JSON recibido en un objeto Item, Mono representa un resultado
                            // reactivo/asíncrono
                            .bodyToMono(Product.class)

                            .map(product -> new Item(product, new Random().nextInt(10) + 1))

                            // Espera de forma síncrona hasta obtener el resultado, convierte el Mono<Item>
                            // en un Item
                            .block());
        } catch (WebClientResponseException e) {
            return Optional.empty();
        }

    }

}
