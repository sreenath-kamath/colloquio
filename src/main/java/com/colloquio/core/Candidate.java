package com.colloquio.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Candidate {

    @JsonProperty("id")
    private long id;

    @JsonProperty("full_name")
    @NotEmpty
    private String fullName;

    @JsonProperty("email")
    @NotEmpty
    private String email;

    @JsonProperty("phone_number")
    @NotEmpty
    private String phoneNumber;

}
