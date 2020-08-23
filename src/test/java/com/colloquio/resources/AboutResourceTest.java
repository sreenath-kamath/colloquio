package com.colloquio.resources;

import com.colloquio.api.Info;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

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