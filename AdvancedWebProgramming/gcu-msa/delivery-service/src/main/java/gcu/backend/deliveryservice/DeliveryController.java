package gcu.backend.deliveryservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final OrderServiceFeignClient orderServiceFeignClient;
    private final DeliveryRepository deliveryRepository;

    @GetMapping("/api/orderdelivery/{id}")
    public String getOrderDelivery(@PathVariable("id") Long id){
        //Receive order information
        Order order = orderServiceFeignClient.getOrder(id);
        //store it in DB
        Delivery delivery = Delivery.builder()
                                .member(order.getMember())
                                .product(order.getProduct())
                                .build();
        deliveryRepository.save(delivery);

        return order.getMember()+ " customer's order : " +
                order.getProduct() +" "+ LocalDate.now() + " Delivery OK";
    }
}
