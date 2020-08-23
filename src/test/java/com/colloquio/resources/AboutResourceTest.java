package com.colloquio.resources;

import com.colloquio.api.Info;
import com.colloquio.core.Candidate;
import com.colloquio.core.InterviewRound;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class AboutResourceTest {

    private static final String organisationName = "hogwarts";
    private static final ResourceExtension resourceExtension = ResourceExtension
            .builder()
            .addResource(new AboutResource(organisationName))
            .build();

    @Test
    public void testSayHello() {
        final Response response = resourceExtension.target("/about").request().get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Info infoResponse = response.readEntity(new GenericType<Info>() {});
        assertEquals(infoResponse.getName(), organisationName);
    }
}