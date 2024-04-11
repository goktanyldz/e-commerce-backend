package com.goktan.ecommercebackend.restImpl;

import com.goktan.ecommercebackend.exception.ProductException;
import com.goktan.ecommercebackend.model.Product;
import com.goktan.ecommercebackend.rest.ProductRest;
import com.goktan.ecommercebackend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductRestImpl implements ProductRest {
    private ProductService productService;

    public ProductRestImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(String category, List<String> color, List<String> size, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Page<Product> res = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
        System.out.println("complete products");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Product> findProductByIdHandler(Long productId) throws ProductException {
        Product product  = productService.findProductById(productId);
        return  new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
    }

   /** @Override
    public ResponseEntity<List<Product>> searchProductHandler(String q) {
        List<Product> products = productService.searchProduct(q);
        return  new ResponseEntity<List<Product>>(products,HttpStatus.OK);
    }*/
}
