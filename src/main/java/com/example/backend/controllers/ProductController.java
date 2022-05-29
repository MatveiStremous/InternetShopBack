package com.example.backend.controllers;

import com.example.backend.models.Product;
import com.example.backend.requests.ProductRequest;
import com.example.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ProductController {
    private  final ProductService productService;

    @PostMapping("addNewProduct")
    public String addProduct(@RequestBody Product product){
        productService.saveProduct(product);
        return "You added a new product!";
    }

    @PostMapping("deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "You deleted product with id: "+id;
    }

    @GetMapping("getProducts")
    public List<ProductRequest> getProducts(){
        return productService.listProducts();
    }

    @GetMapping("getProductInfo/{id}")
    public ResponseEntity<?> getProductInfoById(@PathVariable Long id){
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

}
