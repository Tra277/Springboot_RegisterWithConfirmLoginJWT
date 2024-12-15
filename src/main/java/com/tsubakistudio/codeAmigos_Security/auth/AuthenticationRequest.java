package com.tsubakistudio.codeAmigos_Security.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthenticationRequest {
    private String email;
    private String password;
}
