package com.api.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Employee implements Serializable {
  @Id
  private String id;
  private String matriculate;
  private String firstName;
  private String lastName;
  private String birthDate;
  @Type(type = "text")
  private String image;
  @Enumerated(EnumType.STRING)
  private Sex sex;
  @OneToMany(cascade = CascadeType.ALL)
  private List<PhoneNumber> phoneNumbers;
  private String emailPerso;
  private String emailPro;
  @OneToOne(cascade = CascadeType.ALL)
  private Identity nic;
  @OneToOne(cascade = CascadeType.ALL)
  private Job job;
  private Long children;
  @Enumerated(EnumType.STRING)
  private Category category;
  private String cnaps;

  @PrePersist
  public void generateCustomMatriculate() {
    if (matriculate == null) {
      matriculate = String.format("MAT-EMPLOYEE-%s", Instant.now().toEpochMilli());
    }
  }

  public enum Sex {
    H, F
  }

  public enum Category {
    M1, M2, OS1, OS2, OS3, OP1
  }
}
