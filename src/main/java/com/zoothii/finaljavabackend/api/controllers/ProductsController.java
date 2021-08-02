package com.zoothii.finaljavabackend.api.controllers;

import com.zoothii.finaljavabackend.business.abstracts.ProductService;
import com.zoothii.finaljavabackend.core.utulities.results.DataResult;
import com.zoothii.finaljavabackend.core.utulities.results.ErrorDataResult;
import com.zoothii.finaljavabackend.core.utulities.results.Result;
import com.zoothii.finaljavabackend.entities.concretes.Product;
import com.zoothii.finaljavabackend.entities.dtos.ProductCategoryDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class ProductsController {

    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("products")
    public DataResult<List<Product>> getProducts() {
        return this.productService.getProducts();
    }

    @PostMapping("product")
    public Result createProduct(@Valid @RequestBody Product product) {
        return this.productService.createProduct(product);
    }

    /*@GetMapping("products/{productName}")
    public DataResult<Product> getByProductName(@PathVariable String productName) {
        return this.productService.getByProductName(productName);
    }*/

    @GetMapping("products/{id}")
    public DataResult<Product> getById(@PathVariable int id) {
        return this.productService.getById(id);
    }

    // http://localhost:8181/api/products?and&categoryId=1&productName=Chai
    @GetMapping(value = "products", params = {"and", "productName", "categoryId"})
    public DataResult<Product> getByProductNameAndCategoryId(@RequestParam String productName, @RequestParam int categoryId) {
        return this.productService.getByProductNameAndCategoryId(productName, categoryId);
    }

    // http://localhost:8181/api/products?or&categoryId=1&productName=Chai
    @GetMapping(value = "products", params = {"or", "productName", "categoryId"})
    public DataResult<List<Product>> getByProductNameOrCategoryId(@RequestParam String productName, @RequestParam int categoryId) {
        return this.productService.getByProductNameOrCategoryId(productName, categoryId);
    }

    // http://localhost:8181/api/products?categoryIds=1,8
    @GetMapping(value = "products", params = "categoryIds")
    public DataResult<List<Product>> getByCategoryIdIn(@RequestParam int[] categoryIds) {
        return this.productService.getByCategoryIdIn(categoryIds);
    }

    // http://localhost:8181/api/products?productName=ran
    @GetMapping(value = "products", params = "productName")
    public DataResult<List<Product>> getByProductNameContains(@RequestParam String productName) {
        return this.productService.getByProductNameContains(productName);
    }

    // http://localhost:8181/api/products?starts&productName=Gu
    @GetMapping(value = "products", params = {"starts", "productName"})
    public DataResult<List<Product>> getByProductNameStartsWith(@RequestParam String productName) {
        return this.productService.getByProductNameStartsWith(productName);
    }

    // http://localhost:8181/api/products?and2&categoryId=1&productName=Chai
    @GetMapping(value = "products", params = {"and2", "productName", "categoryId"})
    public DataResult<List<Product>> getByNameAndCategory(@RequestParam String productName, @RequestParam int categoryId) {
        return this.productService.getByNameAndCategory(productName, categoryId);
    }

    // http://localhost:8181/api/products?page=1&pageSize=4
    @GetMapping(value = "products", params = {"page", "pageSize"})
    public DataResult<List<Product>> getProducts(@RequestParam int page, @RequestParam int pageSize) {
        return this.productService.getProducts(page, pageSize);
    }

    // http://localhost:8181/api/products?sort
    @GetMapping(value = "products", params = "sort")
    public DataResult<List<Product>> getProductsSortedByUnitsInStock() {
        return this.productService.getProductsSortedByUnitsInStock();
    }

    // http://localhost:8181/api/products?sortBy=unitPrice
    @GetMapping(value = "products", params = "sortBy")
    public DataResult<List<Product>> getProductsSorted(@RequestParam String sortBy) {
        return this.productService.getProductsSorted(sortBy);
    }

    @GetMapping("products/dto")
    public DataResult<List<ProductCategoryDetailsDto>> getProductCategoryDetails() {
        return this.productService.getProductCategoryDetails();
    }


    /*@GetMapping(value = "products", params = {"productName","categoryId"})
    public DataResult<List<Product>> getByProductNameOrCategoryId(@PathVariable String productName, @PathVariable Integer categoryId){
        return this.productService.getByProductNameOrCategoryId(productName, categoryId);
    }*/

    /*@GetMapping("products/{productName}/or/{categoryId}")
    public DataResult<List<Product>> getByProductNameOrCategoryId(@PathVariable String productName, @PathVariable Integer categoryId){
        return this.productService.getByProductNameOrCategoryId(productName, categoryId);
    }*/

    /*@GetMapping("products/{productName}/and/{categoryId}")
    public DataResult<Product> getByProductNameAndCategoryId(@PathVariable String productName, @PathVariable int categoryId){
        return this.productService.getByProductNameAndCategoryId(productName, categoryId);
    }*/

    /*@GetMapping("/{id}", produces = "application/json")
    public @ResponseBody Book getBook(@PathVariable int id) {
        return findBookById(id);
    }*/


    // ****** VALIDATION ******
    // todo tek seferlik kullanım haline getir
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> validationExceptionHandler(MethodArgumentNotValidException exception){
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ErrorDataResult<>(validationErrors, "validation errors");
    }

}
