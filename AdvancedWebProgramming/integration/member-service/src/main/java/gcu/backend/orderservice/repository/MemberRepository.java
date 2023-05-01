package gcu.backend.orderservice.repository;

import gcu.backend.orderservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "member", path="member")
public interface MemberRepository extends JpaRepository<Member, Long> , CrudRepository<Member, Long>{
//    List<Member> findAllById(@Param("id") Long id);
}