package com.github.dnvriend.repositories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The naming behavior is the same for H2 and MySQL, but the dialect is different, so the generated queries,
 * apart from the table and column names, can be different.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "people") // will create the table 'people'. Note that everything will be forced to be lowercase
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer age;

    @Column(name = "dateOfBirth") // will become 'date_of_birth', but you can put 'date_of_birth' to be more explicit
    private LocalDate birthDate;

    @Column(name = "updated")
    private LocalDateTime lastUpdated;

    @Column(name = "created")
    private LocalDateTime createdAt;
}
