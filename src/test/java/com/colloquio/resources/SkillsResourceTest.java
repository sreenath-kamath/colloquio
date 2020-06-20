package com.colloquio.resources;

import com.colloquio.api.Info;
import com.colloquio.core.Skills;
import com.colloquio.db.SkillsDao;
import com.fasterxml.jackson.databind.JsonNode;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import lombok.Getter;
import lombok.Setter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
public class SkillsResourceTest {

    @Getter
    @Setter
    static class ErrorResponse {

        List<String> errors;

    }

    private static final SkillsDao skillsDao = mock(SkillsDao.class);
    private static final ResourceExtension resourceExtension = ResourceExtension
            .builder()
            .addResource(new SkillsResource(skillsDao))
            .build();
    private Skills seekerSkills;
    private final String skillName = "seeker";
    private final String skillDescription  = "A person who can catch the snitch";

    @BeforeEach
    void setup() {
        seekerSkills = new Skills();
        seekerSkills.setName(skillName);
        seekerSkills.setDescription(skillDescription);
    }

    @AfterEach
    void tearDown() {
        reset(skillsDao);
    }

    @Test
    public void testGetIndividualResource() {
        when(skillsDao.findSkillById(1L)).thenReturn(Optional.ofNullable(seekerSkills));
        Skills skills = resourceExtension.target("/metadata/skills/1").request().get(Skills.class);
        Assertions.assertEquals(skillName, skills.getName());
        Assertions.assertEquals(skillDescription, skills.getDescription());
        verify(skillsDao).findSkillById(1L);
    }

    @Test
    public void testGetIndividualResourceNotFound() {
        when(skillsDao.findSkillById(2L)).thenReturn(Optional.empty());
        final Response response = resourceExtension.target("/metadata/skills/2").request().get();
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode() , response.getStatusInfo().getStatusCode());
        verify(skillsDao).findSkillById(2L);
    }

    @Test
    public void testGetSkillsList() {
        when(skillsDao.getSkills()).thenReturn(Collections.singletonList(seekerSkills));
        final Response response = resourceExtension.target("/metadata/skills").request().get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
        List<Skills> returnedSkills = response.readEntity(new GenericType<List<Skills>>() {});
        Assertions.assertEquals(1, returnedSkills.size());
        Assertions.assertEquals(seekerSkills.getName(), returnedSkills.get(0).getName());
        Assertions.assertEquals(seekerSkills.getDescription(), returnedSkills.get(0).getDescription());
    }

    @Test
    public void testSkillCreationInvalidParams() {
        Skills skills = new Skills();
        Entity<Skills> skillsEntity = Entity.entity(skills, MediaType.APPLICATION_JSON_TYPE);
        final Response response = resourceExtension.target("/metadata/skills").request().post(skillsEntity);
        Assertions.assertEquals(422, response.getStatusInfo().getStatusCode());
        ErrorResponse errorResponse = response.readEntity(new GenericType<ErrorResponse>() {});
        List<String> expectedErrorResponses = new ArrayList<>(Arrays.asList("name must not be empty", "description must not be empty"));
        Assertions.assertEquals(2, errorResponse.getErrors().size());
        assertThat(expectedErrorResponses, Matchers.hasItem("name must not be empty"));
        assertThat(expectedErrorResponses, Matchers.hasItem("description must not be empty"));
    }

    @Test
    public void testSkillsCreation() {
        when(skillsDao.createSkill(any())).thenReturn(123L);
        when(skillsDao.doesSkillExist(any())).thenReturn(0L);
        Entity<Skills> skillsEntity = Entity.entity(seekerSkills, MediaType.APPLICATION_JSON_TYPE);
        final Response response = resourceExtension.target("/metadata/skills").request().post(skillsEntity);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
        Info creationResponse = response.readEntity(new GenericType<Info>() {});
        Assertions.assertEquals(123L, creationResponse.getId());
        Assertions.assertEquals(seekerSkills.getName(), creationResponse.getName());
    }

    @Test
    public void testSkillsCreationDuplicateResource() {
        when(skillsDao.createSkill(any())).thenReturn(123L);
        when(skillsDao.doesSkillExist(any())).thenReturn(1L);
        Entity<Skills> skillsEntity = Entity.entity(seekerSkills, MediaType.APPLICATION_JSON_TYPE);
        final Response response = resourceExtension.target("/metadata/skills").request().post(skillsEntity);
        Assertions.assertEquals(Response.Status.NOT_ACCEPTABLE.getStatusCode(), response.getStatusInfo().getStatusCode());
        JsonNode creationResponse = response.readEntity(new GenericType<JsonNode>() {});
        Assertions.assertEquals("\"Skills with name seeker already exits\"", creationResponse.get("message").toString());
    }
}