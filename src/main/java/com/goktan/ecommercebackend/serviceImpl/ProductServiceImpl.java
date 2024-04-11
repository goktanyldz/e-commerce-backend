package com.goktan.ecommercebackend.serviceImpl;

import com.goktan.ecommercebackend.exception.ProductException;
import com.goktan.ecommercebackend.model.Category;
import com.goktan.ecommercebackend.model.Product;
import com.goktan.ecommercebackend.repository.CategoryRepository;
import com.goktan.ecommercebackend.repository.ProductRepository;
import com.goktan.ecommercebackend.request.CreateProductRequest;
import com.goktan.ecommercebackend.service.ProductService;
import com.goktan.ecommercebackend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.List.of;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;


    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
        if (topLevel==null){
            Category topLevelcategory = new Category();
            topLevelcategory.setName(req.getTopLevelCategory());
            topLevelcategory.setLevel(1);
            topLevel = categoryRepository.save(topLevelcategory);
        }

        Category secondLevel=categoryRepository.findByNameAndParent(req.getSecLevelCategory(),topLevel.getName());

        if (secondLevel==null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);
            secondLevel=categoryRepository.save(secondLevelCategory);
        }
        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThridLevelCategory(), req.getSecLevelCategory());


        if (thirdLevel==null){
            Category thridLevelCategory = new Category();
            thridLevelCategory.setName(req.getThridLevelCategory());
            thridLevelCategory.setParentCategory(secondLevel);
            thridLevelCategory.setLevel(2);
            thirdLevel = categoryRepository.save(thridLevelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPersent(req.getDiscountPersent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAd(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return savedProduct;


    }



    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);

        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product requestProduct) throws ProductException {
        Product product = findProductById(productId);
        if (requestProduct.getQuantity()!=0){
            product.setQuantity(requestProduct.getQuantity());

        }



        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }
        throw new ProductException("Product not found with id- "+ id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {


        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pegable = PageRequest.of(pageNumber,pageSize);

        List<Product> products = productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sort);
        if (!colors.isEmpty()){
            products = products.stream().filter(p->colors.stream().anyMatch(c-> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }

        if (stock!=null){
            if (!stock.equals("in_stock")){

                    products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());


            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());

            }
        }
        int startIndex = (int) pegable.getOffset();
        int endIndex = Math.min(startIndex+ pegable.getPageSize(),products.size());
        List<Product> pageContent = products.subList(startIndex,endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent,pegable, products.size());
        return filteredProducts;
    }
}
