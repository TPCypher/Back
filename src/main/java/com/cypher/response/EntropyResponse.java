package com.cypher.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class EntropyResponse {
    private boolean success;
    private String message;

}
