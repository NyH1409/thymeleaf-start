package com.api.app.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.time.Instant;

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
  private String id;
  private String matriculate;
  private String firstName;
  private String lastName;
  private String birthDate;
  @Type(type = "text")
  private String image;

  @PrePersist
  public void generateCustomMatriculate(){
    if (matriculate == null){
      matriculate = String.format("MAT-EMPLOYEE-%s", Instant.now().toEpochMilli());
    }
  }
}
