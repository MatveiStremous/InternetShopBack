package com.example.backend.services;

import com.example.backend.models.Product;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.requests.ProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public Product findProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public List<ProductRequest> listProducts() {
        List<ProductRequest> productRequestList = new ArrayList<>();
        int size = productRepository.findAll().size();
        for (int i = 0; i < size; i++) {
            ProductRequest temp = new ProductRequest();
            temp.setProductId(productRepository.findAll().get(i).getId());
            temp.setTitle(productRepository.findAll().get(i).getTitle());
            temp.setPrice(productRepository.findAll().get(i).getPrice());
            temp.setImageUrl(productRepository.findAll().get(i).getImageUrl());
            productRequestList.add(temp);
        }

        return productRequestList;
    }

    public void saveProduct(Product product){
        log.info("Saving new {}", product);
        productRepository.save(product);
    }

    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }

   public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
   }
}
