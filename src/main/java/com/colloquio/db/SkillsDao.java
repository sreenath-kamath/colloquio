package com.colloquio.db;

import com.colloquio.core.Skills;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterBeanMapper(Skills.class)
public interface SkillsDao {

    @SqlQuery("select * from skills where id = :id")
    Skills findSkillById(@Bind("id") Long id);


    @SqlUpdate("insert into skills(name, description, created_at, updated_at) values(:name, :description, now(), now())")
    void createSkill(@BindBean final Skills skills);

    @SqlQuery("select last_insert_id()")
    long lastInsertId();
}
