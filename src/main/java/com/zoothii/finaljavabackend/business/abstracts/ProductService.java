package com.zoothii.finaljavabackend.business.abstracts;

import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;
import com.zoothii.finaljavabackend.entities.concretes.Product;
import com.zoothii.finaljavabackend.entities.dtos.ProductCategoryDetailsDto;

import java.util.List;

public interface ProductService {
    DataResult<List<Product>> getProducts();

    Result createProduct(Product product);

    Result deleteProduct(Product product);

    DataResult<Product> getByProductName(String productName);

    DataResult<Product> getById(int productId);

    DataResult<Product> getByProductNameAndCategoryId(String productName, int categoryId);

    DataResult<List<Product>> getByProductNameOrCategoryId(String productName, int categoryId);

    DataResult<List<Product>> getByCategoryIdIn(int[] categoryIds);

    DataResult<List<Product>> getByProductNameContains(String productName);

    DataResult<List<Product>> getByProductNameStartsWith(String productName);

    DataResult<List<Product>> getByNameAndCategory(String productName, int categoryId);

    DataResult<List<Product>> getProducts(int page, int pageSize);

    DataResult<List<Product>> getProductsSortedByUnitsInStock();

    DataResult<List<Product>> getProductsSorted(String sortBy);

    DataResult<List<ProductCategoryDetailsDto>> getProductCategoryDetails();
}
