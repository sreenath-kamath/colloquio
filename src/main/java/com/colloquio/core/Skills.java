package com.colloquio.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Skills {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;
}
