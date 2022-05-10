package com.epalchevskyi.catalogapplication.service;

import com.epalchevskyi.catalogapplication.model.Product;

import java.util.List;

public interface ProductsService {
    List<Product> getProducts(String uniqId, String sku);
}
