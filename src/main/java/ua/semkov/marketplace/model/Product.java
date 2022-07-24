package ua.semkov.marketplace.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    private String name;
    @Column
    private BigDecimal price;

}
