package com.colloquio.db;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface UserDao {

    @SqlQuery("select count(*) from Users where email= :email and password = :password")
    Long doesCredentialsMatch(@Bind("email") String email, @Bind("password") String password);
}
