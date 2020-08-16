package com.colloquio.resources;

import com.colloquio.core.Candidate;
import com.colloquio.core.Skills;
import com.colloquio.db.CandidatesDao;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

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

    private Candidate candidate;
    private String fullName = "Harry Potter";
    private String email = "harry@hogwarts.com";
    private String phoneNumber = "1234567890";

    @BeforeEach
    void setup() {
        candidate = new Candidate();
        candidate.setFullName("Harry Potter");
        candidate.setEmail("harry@hogwarts.com");
        candidate.setPhoneNumber("1234567890");
    }

    @AfterEach
    void tearDown() {
        reset(candidatesDao);
    }

    @Test
    public void testGetIndividualResource() {
        when(candidatesDao.findCandidateById(1L)).thenReturn(Optional.ofNullable(candidate));
        Candidate candidate = resourceExtension.target("/candidate/1").request().get(Candidate.class);
        Assertions.assertEquals(fullName, candidate.getFullName(), "Name doesn't match");
        Assertions.assertEquals(email, candidate.getEmail(), "Email doesn't match");
        Assertions.assertEquals(phoneNumber, candidate.getPhoneNumber(), "PhoneNumber doesn't match");
        verify(candidatesDao).findCandidateById(1L);
    }

}
