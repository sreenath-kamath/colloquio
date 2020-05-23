package com.colloquio.core;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * A model class to hold the interview
 */
@Getter
@Setter
public class Interview {

    @NotNull
    private String interviewId;

}
