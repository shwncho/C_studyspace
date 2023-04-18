package gcu.backend.deliveryservice;

import lombok.Data;

@Data
public class Order {

    private Long id;
    private String member;
    private String product;
}
