package com.colloquio.resources;

import com.colloquio.api.Info;
import com.colloquio.core.Candidate;
import com.colloquio.core.Skills;
import com.colloquio.db.CandidatesDao;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
public class CandidatesResourceTest {

    private static final CandidatesDao candidatesDao = mock(CandidatesDao.class);
    private static final ResourceExtension resourceExtension = ResourceExtension
            .builder()
            .addResource(new CandidatesResouce(candidatesDao))
            .build();

    private Candidate harryPottercandidate;
    private String fullName = "Harry Potter";
    private String email = "harry@hogwarts.com";
    private String phoneNumber = "1234567890";

    @BeforeEach
    void setup() {
        harryPottercandidate = new Candidate();
        harryPottercandidate.setFullName("Harry Potter");
        harryPottercandidate.setEmail("harry@hogwarts.com");
        harryPottercandidate.setPhoneNumber("1234567890");
    }

    @AfterEach
    void tearDown() {
        reset(candidatesDao);
    }

    @Test
    public void testGetIndividualResource() {
        when(candidatesDao.findCandidateById(1L)).thenReturn(Optional.ofNullable(harryPottercandidate));
        Candidate candidate = resourceExtension.target("/candidate/1").request().get(Candidate.class);
        Assertions.assertEquals(fullName, candidate.getFullName(), "Name doesn't match");
        Assertions.assertEquals(email, candidate.getEmail(), "Email doesn't match");
        Assertions.assertEquals(phoneNumber, candidate.getPhoneNumber(), "PhoneNumber doesn't match");
        verify(candidatesDao).findCandidateById(1L);
    }

    @Test
    public void testGetIndividualRecordNotFound() {
        when(candidatesDao.findCandidateById(1L)).thenReturn(Optional.empty());
        final Response response = resourceExtension.target("/candidate/1").request().get();
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode() , response.getStatusInfo().getStatusCode());
        verify(candidatesDao).findCandidateById(1L);
    }

    @Test
    public void testGetCandidateList() {
        when(candidatesDao.getAllCandidates()).thenReturn(Collections.singletonList(harryPottercandidate));
        final Response response = resourceExtension.target("/candidate").request().get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode() , response.getStatusInfo().getStatusCode());
        List<Candidate> candidateList = response.readEntity(new GenericType<List<Candidate>>() {});
        Assertions.assertEquals(1, candidateList.size());
        verify(candidatesDao).getAllCandidates();
    }

    @Test
    public void testSkillCreationInvalidParams() {
        Candidate candidate = new Candidate();
        Entity<Candidate> candidateEntity = Entity.entity(candidate, MediaType.APPLICATION_JSON_TYPE);
        final Response response = resourceExtension.target("/candidate").request().post(candidateEntity);
        Assertions.assertEquals(422, response.getStatusInfo().getStatusCode());
        SkillsResourceTest.ErrorResponse errorResponse = response.readEntity(new GenericType<SkillsResourceTest.ErrorResponse>() {});
        List<String> expectedErrorResponses = errorResponse.getErrors();
        Assertions.assertEquals(3, errorResponse.getErrors().size());
        assertThat(expectedErrorResponses, Matchers.hasItem("fullName must not be empty"));
        assertThat(expectedErrorResponses, Matchers.hasItem("email must not be empty"));
        assertThat(expectedErrorResponses, Matchers.hasItem("phoneNumber must not be empty"));
    }

    @Test
    public void testSkillsCreation() {
        when(candidatesDao.createCandidate(any())).thenReturn(123L);
        when(candidatesDao.doesCandidateExist(any())).thenReturn(0L);
        Entity<Candidate> candidateEntity = Entity.entity(harryPottercandidate, MediaType.APPLICATION_JSON_TYPE);
        final Response response = resourceExtension.target("/candidate").request().post(candidateEntity);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
        Info creationResponse = response.readEntity(new GenericType<Info>() {});
        Assertions.assertEquals(123L, creationResponse.getId());
        Assertions.assertEquals(harryPottercandidate.getFullName(), creationResponse.getName());
    }

    @Test
    public void testSkillsCreationDuplicateResource() {
        when(candidatesDao.createCandidate(any())).thenReturn(123L);
        when(candidatesDao.doesCandidateExist(any())).thenReturn(1L);
        Entity<Candidate> candidateEntity = Entity.entity(harryPottercandidate, MediaType.APPLICATION_JSON_TYPE);
        final Response response = resourceExtension.target("/candidate").request().post(candidateEntity);
        Assertions.assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatusInfo().getStatusCode());
        JsonNode creationResponse = response.readEntity(new GenericType<JsonNode>() {});
        Assertions.assertEquals("\"Candidate with this email_id or phone_number already exits\"", creationResponse.get("message").toString());
    }
}
