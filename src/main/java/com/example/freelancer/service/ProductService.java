package com.example.freelancer.service;

import com.example.freelancer.entity.Product;
import com.example.freelancer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Configurable
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Boolean create(List<Product> products) {
        try {
            productRepository.saveAll(products);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public Iterable<Product> list() {
        return productRepository.findAll();
    }
}
