package gcu.backend.orderservice;

import lombok.Data;

@Data
public class Product {

    private Long id;
    private String productName;
    private Integer price;
}
