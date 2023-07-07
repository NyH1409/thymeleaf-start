package com.api.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Employee {
  private String firstName;
  private String lastName;
  private Date birthDate;
}
