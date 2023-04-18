package gcu.backend.deliveryservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final OrderServiceFeignClient orderServiceFeignClient;

    @GetMapping("/api/orderdelivery/{id}")
    public String getOrderDelivery(@PathVariable("id") Long id){
        Order order = orderServiceFeignClient.getOrder(id);
        return order.getMember()+ " customer's order : " +
                order.getProduct() +" "+ LocalDate.now() + " Delivery OK";
    }
}
