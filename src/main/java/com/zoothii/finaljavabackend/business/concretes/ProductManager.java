package com.zoothii.finaljavabackend.business.concretes;

import com.zoothii.finaljavabackend.business.abstracts.ProductService;
import com.zoothii.finaljavabackend.core.utulities.constants.Messages;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;
import com.zoothii.finaljavabackend.core.utulities.results.SuccessDataResult;
import com.zoothii.finaljavabackend.core.utulities.results.SuccessResult;
import com.zoothii.finaljavabackend.data_access.abstracts.ProductDao;
import com.zoothii.finaljavabackend.entities.concretes.Product;
import com.zoothii.finaljavabackend.entities.dtos.ProductCategoryDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"products"}) // tells where to store cached data
public class ProductManager implements ProductService {

    //@Autowired // this works too but if we have multiple services we can use constructor with only 1 Autowired
    private final ProductDao productDao;

    @Autowired
    public ProductManager(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    @Cacheable(key = "'products-cache'") // caches the result
    public DataResult<List<Product>> getProducts() {
        return new SuccessDataResult<>(this.productDao.findAll(), Messages.successGetProducts);
    }

    @Override
    @CacheEvict(key = "'products-cache'", condition = "#result.success")
    public Result createProduct(Product product) {
        Product productToSave = this.productDao.save(product);
        Product productSaved = this.productDao.findById(productToSave.getId()).get();
        return new SuccessResult(Messages.successCreateProduct + productToSave.getCategory().getCategoryName() + productSaved.getCategory().getCategoryName());
    }

    @Override
    @CacheEvict(key = "'products-cache'", condition = "#result.success")// removes data from cache all data
    public Result deleteProduct(Product product) {
        this.productDao.delete(product);
        return new SuccessResult(Messages.successDeleteProduct);
    }

    @Override
    public DataResult<Product> getByProductName(String productName) {
        return new SuccessDataResult<>(this.productDao.getByProductName(productName));
    }

    @Override
    public DataResult<Product> getById(int productId) {
        return new SuccessDataResult<>(this.productDao.findById(productId).get());
    }

    @Override
    public DataResult<Product> getByProductNameAndCategoryId(String productName, int categoryId) {
        return new SuccessDataResult<>(this.productDao.getByProductNameAndCategoryId(productName, categoryId));
    }

    @Override
    public DataResult<List<Product>> getByProductNameOrCategoryId(String productName, int categoryId) {
        return new SuccessDataResult<>(this.productDao.getByProductNameOrCategoryId(productName, categoryId));
    }

    @Override
    public DataResult<List<Product>> getByCategoryIdIn(int[] categoryIds) {
        return new SuccessDataResult<>(this.productDao.getByCategoryIdIn(categoryIds));

    }

    @Override
    public DataResult<List<Product>> getByProductNameContains(String productName) {
        return new SuccessDataResult<>(this.productDao.getByProductNameContains(productName));
    }

    @Override
    public DataResult<List<Product>> getByProductNameStartsWith(String productName) {
        return new SuccessDataResult<>(this.productDao.getByProductNameStartsWith(productName));
    }

    @Override
    public DataResult<List<Product>> getByNameAndCategory(String productName, int categoryId) {
        return new SuccessDataResult<>(this.productDao.getByNameAndCategory(productName, categoryId));
    }

    @Override
    public DataResult<List<Product>> getProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        return new SuccessDataResult<>(this.productDao.findAll(pageable).getContent());
        //return new SuccessDataResult<>(this.productDao.findAll(pageable).toList());
    }

    @Override
    public DataResult<List<Product>> getProductsSortedByUnitsInStock() {
        Sort sort = Sort.by(Sort.Direction.ASC, "unitsInStock");
        return new SuccessDataResult<>(this.productDao.findAll(sort));
    }

    @Override
    public DataResult<List<Product>> getProductsSorted(String sortBy) {
        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        return new SuccessDataResult<>(this.productDao.findAll(sort));
    }

    @Override
    public DataResult<List<ProductCategoryDetailsDto>> getProductCategoryDetails() {
        return new SuccessDataResult<>(this.productDao.getProductCategoryDetails());
    }
}