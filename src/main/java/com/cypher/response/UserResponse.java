package com.cypher.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UserResponse {
    private boolean success;
    private String token;

}
