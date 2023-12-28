package com.adiha.wealthrating.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents a rich person to be saved in the DB
 */
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "richPeople")
@Builder
@EqualsAndHashCode
@Getter
public class PersonEntity {

    /**
     * Unique identifier for the rich person.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * First name of the rich person.
     */
    private String firstName;

    /**
     * Last name of the rich person.
     */
    private String lastName;

    /**
     * Fortune of the rich person.
     */
    private BigDecimal fortune;
}
