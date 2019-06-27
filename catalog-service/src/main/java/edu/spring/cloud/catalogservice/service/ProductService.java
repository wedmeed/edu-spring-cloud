package edu.spring.cloud.catalogservice.service;

import edu.spring.cloud.catalogservice.dao.ProductRepository;
import edu.spring.cloud.catalogservice.model.internal.Product;
import edu.spring.cloud.catalogservice.model.outcomming.ProductInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public ProductService(ProductRepository productRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductByCode(String code) {
        Optional<Product> result = productRepository.findByCode(code);
        result.ifPresent(product->{
            log.info("Fetching inventory level for product_code: "+code);
            ResponseEntity<ProductInventoryResponse> itemResponseEntity =
                    restTemplate.getForEntity("http://inventory-service/api/inventory/{code}",
                            ProductInventoryResponse.class,
                            code);
            if(itemResponseEntity.getStatusCode() == HttpStatus.OK) {
                int quantity = itemResponseEntity.getBody().getQuantity();
                log.info("Available quantity: "+quantity);
                product.setInStock(quantity> 0);
            } else {
                log.error("Unable to get inventory level for product_code: "+code +
                        ", StatusCode: "+itemResponseEntity.getStatusCode());
            }
        });
        return productRepository.findByCode(code);
    }
}

