package com.colloquio.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InterviewRound {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

}
