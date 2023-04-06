package gcu.backend.memberservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @GetMapping("/member")
    public Member getMember(){
        return new Member(1L,"Gachon","gcu");
    }
}
