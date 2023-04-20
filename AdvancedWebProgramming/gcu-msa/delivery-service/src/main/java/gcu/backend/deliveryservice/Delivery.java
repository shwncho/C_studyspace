package gcu.backend.deliveryservice;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String member;

    private String product;

    private LocalDate deliveryDate;

    private boolean deliveryStatus;

    @Builder
    public Delivery(String member, String product) {
        this.member = member;
        this.product = product;
    }
}
