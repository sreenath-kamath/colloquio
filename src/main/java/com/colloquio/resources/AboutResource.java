package com.colloquio.resources;

import com.codahale.metrics.annotation.Timed;
import com.colloquio.api.Info;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/about")
@Produces(MediaType.APPLICATION_JSON)
public class AboutResource {
    private final AtomicLong counter;
    private final String organisationName;


    public AboutResource(String organisationName) {
        this.counter = new AtomicLong();
        this.organisationName = organisationName;
    }

    @GET
    @Timed
    public Info sayHello() {
        return new Info(counter.incrementAndGet(), this.organisationName);
    }

}
