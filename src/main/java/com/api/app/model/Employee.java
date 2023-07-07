package com.api.app.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Employee {
  private String firstName;
  private String lastName;
  private Date birthDate;
}
