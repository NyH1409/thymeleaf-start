package com.api.app.controller.response;

import lombok.*;

import java.time.LocalDate;
import java.util.Base64;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class RestEmployee {
    private String matriculate;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Base64 image;
}
