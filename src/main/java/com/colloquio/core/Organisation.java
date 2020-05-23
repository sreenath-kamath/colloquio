package com.colloquio.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class Organisation {

    @NotNull
    @JsonProperty("name")
    private String name;

}
