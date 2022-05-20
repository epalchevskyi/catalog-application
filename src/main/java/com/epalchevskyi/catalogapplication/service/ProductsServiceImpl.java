package com.epalchevskyi.catalogapplication.service;

import com.epalchevskyi.catalogapplication.model.Product;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService{
    private static List<Product> PRODUCTS_LIST;

    @PostConstruct
    private void initInMemoryData() {
        String PATH_TO_CSV_FILE = "jcpenney_com-ecommerce_sample.csv";
        File file = new File(PATH_TO_CSV_FILE);

        try {
            PRODUCTS_LIST = new ArrayList<>();
            MappingIterator<Product> personIter = new CsvMapper().readerWithTypedSchemaFor(Product.class).readValues(file);
            PRODUCTS_LIST = personIter.readAll();
        } catch (Exception e) {
            System.out.println("Error while reading data from file " + PATH_TO_CSV_FILE + ". Stack trace " +e.getMessage());
        }
    }

    public List<Product> getProducts(List<String> uniqIds, String sku) {
        List<Product> filteredList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(uniqIds)) {
            filteredList.addAll(PRODUCTS_LIST.stream().filter(product -> uniqIds.contains(product.getUniqId())).collect(Collectors.toList()));
        }

        if (StringUtils.hasText(sku)) {
            filteredList.addAll(PRODUCTS_LIST.stream().filter(product -> product.getSku().equals(sku)).collect(Collectors.toList()));
        }

        return filteredList;
    }
}
