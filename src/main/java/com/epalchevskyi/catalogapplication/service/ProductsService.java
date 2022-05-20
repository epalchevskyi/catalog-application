package com.epalchevskyi.catalogapplication.service;

import com.epalchevskyi.catalogapplication.model.Product;

import java.util.List;

public interface ProductsService {
    List<Product> getProducts(List<String> uniqIds, String sku);
}
