package com.colloquio.resources;


import com.colloquio.core.Skills;
import com.colloquio.db.SkillsDao;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;


import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class SkillsResourceTest {
    private static final SkillsDao skillsDao = mock(SkillsDao.class);
    private static final ResourceExtension resourceExtension = ResourceExtension
            .builder()
            .addResource(new SkillsResource(skillsDao))
            .build();
    private Skills skills;
    private final String skillName = "seeker";
    private final String skillDescription  = "A person who can catch the snitch";

    @BeforeEach
    void setup() {
        skills = new Skills();
        skills.setName(skillName);
        skills.setDescription(skillDescription);
    }

    @AfterEach
    void tearDown() {
        reset(skillsDao);
    }

    @Test
    public void testGetIndividualResource(){
        when(skillsDao.findSkillById(1L)).thenReturn(Optional.ofNullable(skills));
        Skills skills = resourceExtension.target("/metadata/skills/1").request().get(Skills.class);
        Assertions.assertEquals(skillName, skills.getName());
        Assertions.assertEquals(skillDescription, skills.getDescription());
        verify(skillsDao).findSkillById(1L);
    }

    @Test
    public void testGetIndividualResourceNotFound(){
        when(skillsDao.findSkillById(2L)).thenReturn(Optional.empty());
        final Response response = resourceExtension.target("/metadata/skills/2").request().get();
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode() , response.getStatusInfo().getStatusCode());
        verify(skillsDao).findSkillById(2L);
    }

}