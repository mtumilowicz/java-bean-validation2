package com.example.javabeanvalidation2;

import lombok.Builder;
import lombok.Value;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by mtumilowicz on 2018-08-14.
 */
@Entity
@Value
@Builder(toBuilder = true)
class User {

    @Id
    @GeneratedValue
    Integer id;

    @NotNull(message = "Name cannot be null")
    String name;

    @Size(min = 10, max = 30, message
            = "About Me must be between 10 and 200 characters")
    String aboutMe;

    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
    int age;
    
    @NotEmpty(message = "Emails should be not empty")
    @ElementCollection
    List<@NotNull(message = "Email should be not null") @Email(message = "Only valid email") String> emails;
    
    @FutureOrPresent
    LocalDate expirationDate;

    @PositiveOrZero
    int rank;
}
