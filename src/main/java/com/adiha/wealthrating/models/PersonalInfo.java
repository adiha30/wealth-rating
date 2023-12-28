package com.adiha.wealthrating.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents personal information of a person, including first name, last name, and city.
 */
@AllArgsConstructor
@Getter
public class PersonalInfo {

    /**
     * The first name of the person.
     */
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    /**
     * The last name of the person.
     */
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    /**
     * The city where the person resides.
     */
    @NotBlank(message = "City is mandatory")
    private String city;

    /**
     * Gets the full name of the person by combining the first and last names.
     *
     * @return The full name of the person.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
