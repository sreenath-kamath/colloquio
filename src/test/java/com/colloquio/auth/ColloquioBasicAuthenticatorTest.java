package com.colloquio.auth;

import com.colloquio.core.User;
import com.colloquio.db.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ColloquioBasicAuthenticatorTest {

    private UserDao userDao = mock(UserDao.class);
    private ColloquioBasicAuthenticator basicAuthenticator;
    private BasicCredentials basicCredentials;
    private String userName = "harry@hogwarts.com";
    private String password = "hermoine";

    @BeforeEach
    public void setUp() {
        basicAuthenticator = new ColloquioBasicAuthenticator(userDao);
        basicCredentials = new BasicCredentials(userName, password);
    }

    @Test
    void authenticateSuccessCase() throws AuthenticationException {
        when(userDao.doesCredentialsMatch(userName, password)).thenReturn(1L);
        Optional<User> user = basicAuthenticator.authenticate(basicCredentials);
        User returnedUser = null;
        if (user.isPresent()){
            returnedUser = user.get();
        }
        Assertions.assertNotNull(returnedUser);
        Assertions.assertEquals("harry@hogwarts.com", returnedUser.getName());
        Assertions.assertEquals(0, user.get().getRoles().size());
    }

    @Test
    void authenticateNegativeCase() throws AuthenticationException {
        when(userDao.doesCredentialsMatch(userName, password)).thenReturn(0L);
        Optional<User> user = basicAuthenticator.authenticate(basicCredentials);
        User returnedUser = null;
        if (user.isPresent()){
            returnedUser = user.get();
        }
        Assertions.assertNull(returnedUser);
    }
}