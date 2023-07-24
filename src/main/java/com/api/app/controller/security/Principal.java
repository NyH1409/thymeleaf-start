package com.api.app.controller.security;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Principal {
    private String email;
    private String password;
}
