package com.zoothii.finaljavabackend.business.concretes;

import com.zoothii.finaljavabackend.business.abstracts.ProductService;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;
import com.zoothii.finaljavabackend.core.utulities.results.SuccessDataResult;
import com.zoothii.finaljavabackend.data_access.abstracts.ProductDao;
import com.zoothii.finaljavabackend.entities.concretes.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductManager implements ProductService {

    //@Autowired // this works too but if we have multiple services we can use constructor with only 1 Autowired
    private final ProductDao productDao;

    @Autowired
    public ProductManager(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public DataResult<List<Product>> getProducts() {
        return new SuccessDataResult<>(this.productDao.findAll(), "products returned successfully");
    }

    @Override
    public DataResult<Product> createProduct(Product product) {
        return new SuccessDataResult<>(this.productDao.save(product), "product saved successfully");
    }
}
