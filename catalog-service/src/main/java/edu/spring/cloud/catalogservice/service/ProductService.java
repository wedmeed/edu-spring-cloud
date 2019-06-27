package edu.spring.cloud.catalogservice.service;

import edu.spring.cloud.catalogservice.dao.ProductRepository;
import edu.spring.cloud.catalogservice.model.internal.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryServiceClient inventoryServiceClient;


    @Autowired
    public ProductService(ProductRepository productRepository,
                          InventoryServiceClient inventoryServiceClient) {
        this.productRepository = productRepository;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductByCode(String code) {
        Optional<Product> result = productRepository.findByCode(code);
        result.ifPresent(product->{
            log.info("1: " + Thread.currentThread());
            log.info("Fetching inventory level for product_code: "+code);
            inventoryServiceClient.getProductInventoryByCode(code).ifPresent(response->{
                int quantity = response.getQuantity();
                log.info("Available quantity: "+quantity);
                product.setInStock(quantity> 0);
            });
            log.info("4: " + Thread.currentThread());
        });
        return result;
    }
}

