package com.colloquio.resources;

import com.colloquio.api.Info;
import com.colloquio.core.Candidate;
import com.colloquio.core.InterviewRound;
import com.colloquio.db.InterviewRoundDao;
import com.fasterxml.jackson.databind.JsonNode;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.hamcrest.Matchers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
class InterviewRoundsResourceTest {

    private static final InterviewRoundDao interviewRoundDao = mock(InterviewRoundDao.class);
    private static final ResourceExtension resourceExtension = ResourceExtension
            .builder()
            .addResource(new InterviewRoundsResource(interviewRoundDao))
            .build();
    private static InterviewRound interviewRound;
    private static String interviewRoundName = "potion making";
    private static String interviewRoundDescription = "Aimed at testing the candidates skills in preparation of potions";

    @BeforeEach
    public void setup(){
        interviewRound = new InterviewRound();
        interviewRound.setName(interviewRoundName);
        interviewRound.setDescription(interviewRoundDescription);
    }

    @AfterEach
    public void tearDown() {
        reset(interviewRoundDao);
    }

    @Test
    public void testInterviewRoundCreationMissingParams() {
        InterviewRound interviewRound = new InterviewRound();
        Entity<InterviewRound> interviewRoundEntity = Entity.entity(interviewRound, MediaType.APPLICATION_JSON_TYPE);
        final Response response = resourceExtension.target("/interview_rounds").request().post(interviewRoundEntity);
        Assertions.assertEquals(422, response.getStatusInfo().getStatusCode());
        SkillsResourceTest.ErrorResponse errorResponse = response.readEntity(new GenericType<SkillsResourceTest.ErrorResponse>() {});
        List<String> expectedErrorResponses = errorResponse.getErrors();
        Assertions.assertEquals(2, errorResponse.getErrors().size());
        assertThat(expectedErrorResponses, Matchers.hasItem("name must not be empty"));
        assertThat(expectedErrorResponses, Matchers.hasItem("description must not be empty"));
    }

    @Test
    public void testInterviewRoundCreation() {
        when(interviewRoundDao.createInterviewRound(any())).thenReturn(123L);
        when(interviewRoundDao.doesInterviewRoundExist(any())).thenReturn(0L);
        Entity<InterviewRound> interviewRoundEntity = Entity.entity(interviewRound, MediaType.APPLICATION_JSON_TYPE);
        final Response response = resourceExtension.target("/interview_rounds").request().post(interviewRoundEntity);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
        Info infoResponse = response.readEntity(new GenericType<Info>() {});
        Assertions.assertEquals(123L, infoResponse.getId());
        Assertions.assertEquals(interviewRound.getName(), infoResponse.getName());
    }

    @Test
    public void testInterviewRoundCreationDuplicateResource() {
        when(interviewRoundDao.createInterviewRound(any())).thenReturn(123L);
        when(interviewRoundDao.doesInterviewRoundExist(any())).thenReturn(1L);
        Entity<InterviewRound> interviewRoundEntity = Entity.entity(interviewRound, MediaType.APPLICATION_JSON_TYPE);
        final Response response = resourceExtension.target("/interview_rounds").request().post(interviewRoundEntity);
        Assertions.assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatusInfo().getStatusCode());
        JsonNode creationResponse = response.readEntity(new GenericType<JsonNode>() {});
        Assertions.assertEquals("\"InterviewRound with this name already exits\"", creationResponse.get("message").toString());
    }
}