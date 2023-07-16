package com.tinyblu.backend.api;

import com.tinyblu.backend.business.ProductBusiness;
import com.tinyblu.backend.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductAPI {

    private final ProductBusiness business;

    public ProductAPI(ProductBusiness business) {
        this.business = business;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductId(@PathVariable("id") String id) throws BaseException {
        String response = business.getProductById(id);
        return ResponseEntity.ok(response);
    }
}

