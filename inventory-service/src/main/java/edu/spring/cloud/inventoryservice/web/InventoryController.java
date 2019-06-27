package edu.spring.cloud.inventoryservice.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@Slf4j
public class InventoryController {


    @GetMapping("/api/inventory/{productCode}")
    public ResponseEntity<Map> findInventoryByProductCode(@PathVariable("productCode") String productCode) {
        log.info("provide stab response");
        if(StringUtils.isNotBlank(productCode)) {
            return new ResponseEntity(Collections.singletonMap("quantity", productCode.length()), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }
}
