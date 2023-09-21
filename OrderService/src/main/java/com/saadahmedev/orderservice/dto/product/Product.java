package com.saadahmedev.orderservice.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private long id;
    private String title;
    private String description;
    private double price;
    private int discount;
    private int itemCount;
    private List<Image> images;
    private String createdAt;
    private String updatedAt;
}
