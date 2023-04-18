package gcu.backend.orderservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("productservice")
public interface ProductServiceFeignClient {

    @GetMapping(value="/api/product/{id}", consumes = "application/json")
    Product getProductId(@PathVariable Long id);
}
