package com.zoothii.finaljavabackend.business.concretes;

import com.zoothii.finaljavabackend.business.abstracts.ProductService;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;
import com.zoothii.finaljavabackend.core.utulities.results.SuccessDataResult;
import com.zoothii.finaljavabackend.core.utulities.results.SuccessResult;
import com.zoothii.finaljavabackend.data_access.abstracts.ProductRepository;
import com.zoothii.finaljavabackend.entities.concretes.Product;
import com.zoothii.finaljavabackend.entities.dtos.ProductCategoryDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ProductManager implements ProductService {

    //@Autowired // this works too but if we have multiple services we can use constructor with only 1 Autowired
    private final ProductRepository productRepository;

    @Autowired
    public ProductManager(ProductRepository productDao) {
        this.productRepository = productDao;
    }

    @Override
    public DataResult<List<Product>> getProducts() {
        return new SuccessDataResult<>(this.productRepository.findAll(), "products returned successfully");
    }

    @Override
    public Result createProduct(Product product) {
        Product productToSave = this.productRepository.save(product);
        Product productSaved = this.productRepository.findById(productToSave.getId()).get();
        return new SuccessResult("product saved successfully"  + productToSave.getCategory().getCategoryName() + productSaved.getCategory().getCategoryName());
    }

    @Override
    public DataResult<Product> getByProductName(String productName) {
        return new SuccessDataResult<>(this.productRepository.getByProductName(productName));
    }

    @Override
    public DataResult<Product> getById(int productId) {
        return new SuccessDataResult<>(this.productRepository.findById(productId).get());
    }

    @Override
    public DataResult<Product> getByProductNameAndCategoryId(String productName, int categoryId) {
        return new SuccessDataResult<>(this.productRepository.getByProductNameAndCategoryId(productName, categoryId));
    }

    @Override
    public DataResult<List<Product>> getByProductNameOrCategoryId(String productName, int categoryId) {
        return new SuccessDataResult<>(this.productRepository.getByProductNameOrCategoryId(productName, categoryId));
    }

    @Override
    public DataResult<List<Product>> getByCategoryIdIn(int[] categoryIds) {
        return new SuccessDataResult<>(this.productRepository.getByCategoryIdIn(categoryIds));

    }

    @Override
    public DataResult<List<Product>> getByProductNameContains(String productName) {
        return new SuccessDataResult<>(this.productRepository.getByProductNameContains(productName));
    }

    @Override
    public DataResult<List<Product>> getByProductNameStartsWith(String productName) {
        return new SuccessDataResult<>(this.productRepository.getByProductNameStartsWith(productName));
    }

    @Override
    public DataResult<List<Product>> getByNameAndCategory(String productName, int categoryId) {
        return new SuccessDataResult<>(this.productRepository.getByNameAndCategory(productName, categoryId));
    }

    @Override
    public DataResult<List<Product>> getProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        return new SuccessDataResult<>(this.productRepository.findAll(pageable).getContent());
        //return new SuccessDataResult<>(this.productDao.findAll(pageable).toList());
    }

    @Override
    public DataResult<List<Product>> getProductsSortedByUnitsInStock() {
        Sort sort = Sort.by(Sort.Direction.ASC, "unitsInStock");
        return new SuccessDataResult<>(this.productRepository.findAll(sort));
    }

    @Override
    public DataResult<List<Product>> getProductsSorted(String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        return new SuccessDataResult<>(this.productRepository.findAll(sort));
    }

    @Override
    public DataResult<List<ProductCategoryDetailsDto>> getProductCategoryDetails() {
        return new SuccessDataResult<>(this.productRepository.getProductCategoryDetails());
    }
}