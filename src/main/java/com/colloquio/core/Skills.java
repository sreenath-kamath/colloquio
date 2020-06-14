package com.colloquio.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class Skills {

    @JsonProperty("id")
    private long id;
    
    @JsonProperty("name")
    @NotEmpty
    private String name;

    @JsonProperty("description")
    @NotEmpty
    private String description;
}
