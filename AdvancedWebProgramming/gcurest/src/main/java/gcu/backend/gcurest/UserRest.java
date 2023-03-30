package gcu.backend.gcurest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRest {

    private final UserDaoService service;

    public UserRest(final UserDaoService userDaoService){
        this.service = userDaoService;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        return service.findOne(id);
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User user){
        service.updateUser(id,user);
        return "Update OK";
    }


}
