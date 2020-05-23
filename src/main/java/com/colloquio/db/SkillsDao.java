package com.colloquio.db;

import com.colloquio.core.Skills;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.AllowUnusedBindings;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface SkillsDao {

    @SqlQuery("select * from skills where id = :id")
    @RegisterBeanMapper(Skills.class)
    Skills findSkillById(@Bind("id") Long id);

    @AllowUnusedBindings
    @SqlQuery("insert into skills(name, description, created_at, updated_at) values(:name, :description, now(), now())")
    @GetGeneratedKeys
    long createSkill(@Bind("name") String name, @Bind("description") String description);
}
