package gcd.backend.gcucar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CarController {

    @RequestMapping("/")
    public String index(){ return "index";}

    @RequestMapping(value="/result", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute Car car){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("car-data");
        modelAndView.addObject("car",car);
        return modelAndView;
    }
}


