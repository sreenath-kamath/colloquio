package com.colloquio.core;

import lombok.Getter;
import lombok.Setter;

import java.security.Principal;
import java.util.Set;

@Getter
@Setter
public class User implements Principal {

    private final String name;

    private final Set<String> roles;

    public User(String name, Set<String> roles) {
        this.name = name;
        this.roles = roles;
    }
}
