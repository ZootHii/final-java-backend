package com.zoothii.finaljavabackend.business.abstracts;

import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;
import com.zoothii.finaljavabackend.entities.concretes.Product;

import java.util.List;

public interface ProductService {
    DataResult<List<Product>> getProducts();
    DataResult<Product> createProduct(Product product);
}
