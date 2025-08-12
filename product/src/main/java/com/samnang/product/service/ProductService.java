package com.samnang.product.service;

import com.samnang.product.dto.ProductRequest;
import com.samnang.product.dto.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    boolean deleteProduct(Long id);

    List<ProductResponse> searchProducts(String keyword);

    Optional<ProductResponse> getProductById(String id);
}
