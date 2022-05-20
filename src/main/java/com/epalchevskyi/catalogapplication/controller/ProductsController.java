package com.epalchevskyi.catalogapplication.controller;

import com.epalchevskyi.catalogapplication.model.Product;
import com.epalchevskyi.catalogapplication.service.ProductsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductsController {
    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String uniqIds, @RequestParam(required = false) String sku) {
        List<String> parsedIds = new ArrayList<>();

        if (StringUtils.hasText(uniqIds)) {
            parsedIds = Arrays.asList(uniqIds.split(","));
        }
        return productsService.getProducts(parsedIds, sku);
    }
}
