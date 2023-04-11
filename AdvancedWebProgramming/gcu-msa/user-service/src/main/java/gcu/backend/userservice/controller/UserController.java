package gcu.backend.userservice.controller;

import gcu.backend.userservice.domain.User;
import gcu.backend.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/api/user")
    public String create(@RequestBody User user){
        userRepository.save(user);
        return "create ok";
    }

    @GetMapping("/api/user/{id}")
    public Optional<User> show(@PathVariable("id") Long id){
        return userRepository.findById(id);
    }

    @GetMapping("/api/users")
    public List<User> showAll(){
        return userRepository.findAll();
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity update(@PathVariable("id") Long id,
                                 @RequestBody User user){
        Optional<User> userUpdate = userRepository.findById(id);
        if(userUpdate.isPresent()){
            User _user = userUpdate.get();
            _user.setName(user.getName());
            _user.setAddress(user.getAddress());
            _user.setAge(user.getAge());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/api/user/{id}")
    public String delete(@PathVariable("id") Long id){
        userRepository.deleteById(id);
        return "delete ok";
    }
}
