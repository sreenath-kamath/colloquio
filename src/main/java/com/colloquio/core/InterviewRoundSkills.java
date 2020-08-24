package com.colloquio.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InterviewRoundSkills {

    @JsonProperty("id")
    private long id;

    @JsonProperty("interview_round")
    private InterviewRound interviewRound;

    @JsonProperty("skills")
    private Skills skills;
    
}
