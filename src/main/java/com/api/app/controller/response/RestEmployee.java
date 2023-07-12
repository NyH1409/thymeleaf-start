package com.api.app.controller.response;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

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
    private String birthDate;
    private MultipartFile image;
}
