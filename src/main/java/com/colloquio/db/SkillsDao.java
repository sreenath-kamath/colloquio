package com.colloquio.db;

import com.colloquio.core.Skills;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface SkillsDao {

    @SqlQuery("select * from skills where id = :id")
    @RegisterBeanMapper(Skills.class)
    Skills findSkillById(@Bind("id") Long id);

}
