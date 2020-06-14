package com.colloquio;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.validation.Validators;
import io.dropwizard.util.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class ColloquioConfigurationTest {

    private final ObjectMapper objectMapper = Jackson.newObjectMapper();
    private final Validator validator = Validators.newValidator();
    private final YamlConfigurationFactory<ColloquioConfiguration> factory =
            new YamlConfigurationFactory<>(ColloquioConfiguration.class, validator, objectMapper, "dw");

    private ColloquioConfiguration configuration;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException, ConfigurationException {
        final File yml = new File(Resources.getResource("config/test_config.yml").toURI());
        configuration = factory.build(yml);
    }

    @Test
    public void testOrganisationConfiguration() {
        Assertions.assertEquals("Hogwarts", configuration.getOrganisation().getName());
    }

    @Test
    public void testDataBaseConfiguration() {
        Assertions.assertEquals("harry", configuration.getDataSourceFactory().getUser());
        Assertions.assertEquals("potter", configuration.getDataSourceFactory().getPassword());
    }

}