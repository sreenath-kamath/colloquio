package com.colloquio.db;

import com.colloquio.core.Skills;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface SkillsDao {

    @SqlQuery("select Name from skills where id = :id")
    String findSkillById(@Bind("id") Long id);

}
