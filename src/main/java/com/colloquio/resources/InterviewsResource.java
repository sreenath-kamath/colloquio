package com.colloquio.resources;

import com.codahale.metrics.annotation.Metered;
import com.colloquio.api.Info;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicLong;

@Path("/interviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InterviewsResource {

    private final AtomicLong interviewCounter;

    public InterviewsResource() {
        this.interviewCounter = new AtomicLong();
    }

    @GET
    @Metered
    public Info getInterviewDetails(
            @QueryParam("id") @NotNull Long interviewId
    ){
        //TODO Fetch the interview Details from the BackEnd DataBase
        return new Info(this.interviewCounter.incrementAndGet(), "Test");
    }
}
