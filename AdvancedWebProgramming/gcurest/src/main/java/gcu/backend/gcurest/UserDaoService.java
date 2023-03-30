package gcu.backend.gcurest;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDaoService {
    public static int userCount=5;
    private static List<User> users = new ArrayList<>();

    static{
        users.add(new User(1,"gasoon",new Date()));
        users.add(new User(2,"kakao",new Date()));
        users.add(new User(3,"kason",new Date()));
        users.add(new User(4,"heycacao",new Date()));
        users.add(new User(5,"gachon",new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User findOne(int id){
        for(User user: users){
            if(user.getId()==id)
                return user;
        }
        return null;
    }

    public void updateUser(int id, User requser){
        for(User user : users){
            if(user.getId() == id){
                user.setName(requser.getName());
                user.setDob(new Date());
            }
        }
    }
}
