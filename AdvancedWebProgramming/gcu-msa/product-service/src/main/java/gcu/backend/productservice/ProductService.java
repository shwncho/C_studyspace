package gcu.backend.productservice;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private static List<Product> products = new ArrayList<>();

    static{
        products.add(new Product(1L,"smartRobot",5000));
        products.add(new Product(2L,"smartCar",3000));
        products.add(new Product(3L,"smartNotebook",2000));
    }

    public Product findOne(Long id){
        for(Product product: products){
            if(product.getId()==id){
                return product;
            }
        }

        return null;
    }
}
