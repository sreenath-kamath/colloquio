package com.colloquio.resources;

import com.codahale.metrics.annotation.Timed;
import com.colloquio.api.Info;
import com.colloquio.core.Candidate;
import com.colloquio.core.InterviewRound;
import com.colloquio.db.InterviewRoundDao;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/interview_rounds")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InterviewRoundsResource {

    private final InterviewRoundDao interviewRoundDao;

    public InterviewRoundsResource(InterviewRoundDao interviewRoundDao) {
        this.interviewRoundDao = interviewRoundDao;
    }

    @POST
    @Timed
    @PermitAll
    public Info createInterviewRound(
            @Valid @NotNull InterviewRound interviewRound){
        long interviewRoundCount = interviewRoundDao.doesInterviewRoundExist(interviewRound);
        if (interviewRoundCount > 0) {
            throw new NotAcceptableException("InterviewRound with this name already exits");
        }
        long interviewRoundId = interviewRoundDao.createInterviewRound(interviewRound);
        return new Info(interviewRoundId, interviewRound.getName());
    }
}
