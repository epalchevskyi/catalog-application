package com.epalchevskyi.catalogapplication.service;

import com.epalchevskyi.catalogapplication.model.Product;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService{
    private static Map<String, Product> PRODUCTS_MAP;
    private static List<Product> PRODUCTS_LIST;

    @PostConstruct
    private void initInMemoryData() {
        String PATH_TO_CSV_FILE = "jcpenney_com-ecommerce_sample.csv";
        File file = new File(PATH_TO_CSV_FILE);

        try {
            PRODUCTS_LIST = new ArrayList<>();
            PRODUCTS_MAP = new HashMap<>();
            MappingIterator<Product> personIter = new CsvMapper().readerWithTypedSchemaFor(Product.class).readValues(file);
            PRODUCTS_LIST = personIter.readAll();

            PRODUCTS_LIST.forEach(product -> PRODUCTS_MAP.put(product.getUniqId(), product));
        } catch (Exception e) {
            System.out.println("Error while reading data from file " + PATH_TO_CSV_FILE + ". Stack trace " +e.getMessage());
        }
    }

    public List<Product> getProducts(String uniqId, String sku) {
        if (uniqId != null) {
            return Collections.singletonList(PRODUCTS_MAP.get(uniqId));
        } else if (sku != null) {
            return PRODUCTS_LIST.stream().filter(product -> product.getSku().equals(sku)).collect(Collectors.toList());
        } else {
            return PRODUCTS_LIST;
        }
    }
}
