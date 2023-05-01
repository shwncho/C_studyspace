package gcu.backend.orderservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
public class Member implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;
}