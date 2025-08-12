package com.samnang.product.service.impl;

import com.samnang.product.model.Product;
import com.samnang.product.repositories.ProductRepository;
import com.samnang.product.dto.ProductRequest;
import com.samnang.product.dto.ProductResponse;
import com.samnang.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct) ;
    }

    @Override
    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id).map(existingProduct -> {
            updateProductFromRequest(existingProduct,productRequest);
            Product savedProduct = productRepository.save(existingProduct);
            return mapToProductResponse(savedProduct) ;
        });

    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    @Override
    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                        .map(product -> {
                            product.setActive(false);
                            productRepository.save(product);
                            return true;
                        }).orElse(false);
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword)
                .stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductResponse> getProductById(String id) {
       return productRepository.findByIdAndActiveTrue(Long.valueOf(id))
                .map(this::mapToProductResponse);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setCategory(savedProduct.getCategory());
        productResponse.setStockQty(savedProduct.getStockQty());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        productResponse.setActive(savedProduct.getActive());
        return productResponse;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setStockQty(productRequest.getStockQty());
        product.setImageUrl(productRequest.getImageUrl());
        //product.setActive(productRequest.getActive());

    }
}
