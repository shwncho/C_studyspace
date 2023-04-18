package gcu.backend.deliveryservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("orderservice")
public interface OrderServiceFeignClient {

    @GetMapping(value="/api/order/{id}", consumes = "application/json")
    Order getOrder(@PathVariable Long id);
}
