package gcu.backend.orderservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("memberservice")
public interface MemberServiceFeignClient {

    @GetMapping(value="/api/member/{id}", consumes="application/json")
    Member getMember(@PathVariable Long id);

    @GetMapping(value="/api/members", consumes="application/json")
    Member getMembers();
}
