package gcu.backend.orderservice;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private MemberServiceFeignClient memberServiceFeignClient;

    @Autowired
    private ProductServiceFeignClient productServiceFeignClient;

    @GetMapping("/order")
    public String order(){
        return memberServiceFeignClient.getMember().getName() + "requested an order.";
    }

    @GetMapping("/order/{id}")
    public String orderId(@PathVariable("id") Long id){
        return memberServiceFeignClient.getMember().getName() +
                " "+
                productServiceFeignClient.getProductId(id).getProductName() +
                " "+
                " requested an order.";
    }
}
