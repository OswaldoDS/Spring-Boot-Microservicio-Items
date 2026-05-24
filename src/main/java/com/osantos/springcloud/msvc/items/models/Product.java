package com.osantos.springcloud.msvc.items.models;

import java.time.LocalDate;

//Es una clase Data Transfer Objet DTO, los objetos que se obtienen desde algun lugar (desde un cliente o un servidor) que viajan con una estructura JSON
public class Product {

    private Long id;
    private String name;
    private Double price;
    private LocalDate createAt;
    private int port;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
