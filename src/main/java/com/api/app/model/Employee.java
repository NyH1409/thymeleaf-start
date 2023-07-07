package com.api.app.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String matriculate;
  private String firstName;
  private String lastName;
  private LocalDate birthDate;
  private String image;
}
