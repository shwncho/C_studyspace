package gcu.backend.orderservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final MemberServiceFeignClient memberServiceFeignClient;
    private final ProductServiceFeignClient productServiceFeignClient;
    private final OrderRepository orderRepository;


    @GetMapping("/api/order")
    public String getOrders(){
        return memberServiceFeignClient.getMembers().getName() + " requested an order.";
    }

    @GetMapping("/api/order/{id}")
    public Order getOrder(@PathVariable("id") Long id){
        return orderRepository.findById(id).get();
    }

    @PostMapping("/api/order")
    public String createOrder(@RequestBody Order order){
        orderRepository.save(order);
        return "create ok";
    }
}
