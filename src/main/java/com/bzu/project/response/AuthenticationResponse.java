package com.bzu.project.response;


import lombok.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {
    private final String token;
}