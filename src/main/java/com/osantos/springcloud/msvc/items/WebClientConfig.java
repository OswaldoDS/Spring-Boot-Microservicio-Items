package com.osantos.springcloud.msvc.items;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

//Clase para configuración en el Web Client y poder implementar un nuevo servicio
@Configuration
public class WebClientConfig {

    @Value("${config.base-url.endpoint.msvc-products}")
    private String url;

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        // Nota: en Spring Cloud LoadBalancer / Eureka, solo representa el nombre lógico
        // del servicio, el path del endpoint debes ponerlo tú.

        return WebClient.builder().baseUrl(url); // Devuelve un componente spring y balanceo de carga
    }
}
