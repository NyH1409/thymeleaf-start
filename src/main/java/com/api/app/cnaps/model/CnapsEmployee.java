package com.api.app.cnaps.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CnapsEmployee implements Serializable {
    @Id
    private String id;
    private String matriculate;
    private String firstName;
    private String lastName;
    private String birthDate;
    @Type(type = "text")
    private String image;
    private String sex;
    private String emailPerso;
    private String emailPro;
    private Long children;
    @Enumerated(EnumType.STRING)
    private com.api.app.model.Employee.Category category;
    private String entranceDate;
    private String leavingDate;

    @PrePersist
    public void generateCustomMatriculate() {
        if (matriculate == null) {
            matriculate = String.format("MAT-CNAPS-EMPLOYEE-%s", Instant.now().toEpochMilli());
        }
    }

    public enum Category {
        M1, M2, OS1, OS2, OS3, OP1
    }
}
