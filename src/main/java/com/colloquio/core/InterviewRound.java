package com.colloquio.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class InterviewRound {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    @NotEmpty
    private String name;

    @JsonProperty("description")
    @NotEmpty
    private String description;

}
