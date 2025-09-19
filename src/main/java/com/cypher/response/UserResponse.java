package com.cypher.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String email;

}
