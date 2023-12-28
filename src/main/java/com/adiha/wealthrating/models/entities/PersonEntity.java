package com.adiha.wealthrating.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "richPeople")
@Builder
@EqualsAndHashCode
@Getter
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private BigDecimal fortune;
}
