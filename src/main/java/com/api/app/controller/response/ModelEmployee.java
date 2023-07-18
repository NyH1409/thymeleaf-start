package com.api.app.controller.response;

import com.api.app.model.Identity;
import com.api.app.model.Job;
import com.api.app.model.PhoneNumber;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
public class ModelEmployee {
    private String matriculate;
    private String firstName;
    private String lastName;
    private String birthDate;
    private MultipartFile image;
    private String sex;
    private PhoneNumber phoneNumber;
    private String emailPerso;
    private String emailPro;
    private Identity nic;
    private Job job;
    private Long children;
    private String category;
    private String cnaps;
}
