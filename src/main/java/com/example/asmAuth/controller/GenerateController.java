package com.example.asmAuth.controller;

import com.example.asmAuth.dto.LoginDTO;
import com.example.asmAuth.entity.Account;
import com.example.asmAuth.entity.Product;
import com.example.asmAuth.service.AccountService;
import com.example.asmAuth.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/generate")
public class GenerateController {
    @Autowired
    ProductService productService;

    @Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public Boolean generate() {
        List<Product> products = Arrays.asList(
                new Product("Tivi Samsung", 15000000),
                new Product("Tivi LG", 16000000),
                new Product("Tivi Toshiba", 18000000),
                new Product("Máy giặt Samsung", 10000000),
                new Product("Máy giặt beko", 25000000),
                new Product("Máy giặt LG", 8000000),
                new Product("Điều hòa Daikin", 15000000),
                new Product("Điều hòa LG", 25000000),
                new Product("Điều hòa Samsung", 18000000),
                new Product("Nồi cơm Happy Cook", 5000000)
        );
        productService.create(products);
        accountService.createAcount("bangnd",  "Nguyễn Đăng Bằng","123456",1,1);
        accountService.createAcount("minhduc",  "Minh Đức","123456",1,0);
        accountService.createAcount("hongtruong",  "Hồng Trường","123456",2,1);
        accountService.createAcount("tuankien",  "Tuấn Kiên","123456",2,0);
        return true;
    }
    @RequestMapping(method = RequestMethod.GET,value = "/test")
    public String test() {
        return "Test thành công";
    }
}
