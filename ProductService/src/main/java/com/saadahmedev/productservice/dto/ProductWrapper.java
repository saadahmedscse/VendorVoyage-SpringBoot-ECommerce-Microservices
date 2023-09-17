package com.saadahmedev.productservice.dto;

import com.saadahmedev.productservice.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductWrapper {
    private long id;
    private String title;
    private String description;
    private double price;
    private int discount;
    private List<Image> images;
    private boolean isAvailable;
    private int inStock;
    private String createdAt;
    private String updatedAt;
}
