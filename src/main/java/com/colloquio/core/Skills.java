package com.colloquio.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skills {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}
