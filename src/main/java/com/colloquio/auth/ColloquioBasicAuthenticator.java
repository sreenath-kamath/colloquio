package com.colloquio.auth;

import com.colloquio.core.User;
import com.colloquio.db.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Collections;
import java.util.Optional;

public class ColloquioBasicAuthenticator implements Authenticator<BasicCredentials, User> {

    private final UserDao userDao;

    public ColloquioBasicAuthenticator(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        Long validationResult = userDao.doesCredentialsMatch(
                basicCredentials.getUsername(), basicCredentials.getPassword());
        if (validationResult == 1){
            return Optional.of(new User(basicCredentials.getUsername(), Collections.emptySet()));
        }
        return Optional.empty();
    }
}
