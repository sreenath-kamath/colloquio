package com.colloquio.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/***
 * An API response for Information About the Firm
 */
@Getter
@Setter
public class Info {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Id")
    private long id;

    public Info(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Info() {

    }

}
