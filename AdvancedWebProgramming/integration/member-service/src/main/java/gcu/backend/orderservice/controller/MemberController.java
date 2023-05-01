package gcu.backend.orderservice.controller;


import gcu.backend.orderservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import gcu.backend.orderservice.domain.Member;

import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
public class MemberController {
    @Autowired
    private final  MemberRepository memberRepository;
    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @GetMapping("/api/member/{id}")
    public Optional<Member> getMemberId(@PathVariable("id") Long id ){
            return  memberRepository.findById(id);
        }

   @GetMapping("/")
     public Member index(){
         Member member = new Member(1, "gachon", "gcu");
         return member;
        }
    @PostMapping("/api/member")
    public String create(@RequestBody Member member) {
        memberRepository.save(member);
        return "member create ok";
    }
}

