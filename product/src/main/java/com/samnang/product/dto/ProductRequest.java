package com.samnang.product.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private String price;
    private Integer stockQty;
    private String category;
    private String imageUrl;
}
