package gcu.backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {

    @GetMapping(value = "not-cors")
    public String notCors(){
        System.out.println("not-cors");
        return "notCors";
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping(value = "/cors")
    public String cors(){
        System.out.println("cors");
        return "cors";
    }
}
