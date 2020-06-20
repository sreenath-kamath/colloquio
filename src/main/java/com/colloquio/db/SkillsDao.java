package com.colloquio.db;

import com.colloquio.core.Skills;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Skills.class)
public interface SkillsDao {

    @SqlQuery("select * from skills where id = :id")
    Optional<Skills> findSkillById(@Bind("id") Long id);

    @SqlQuery("insert into skills(name, description, created_at, updated_at) values(:name, :description, now(), now()) RETURNING id")
    long createSkill(@BindBean final Skills skills);

    @SqlQuery("select id, name, description from skills")
    List<Skills> getSkills();

    @SqlQuery("select count(*) from skills where name = :name")
    Long doesSkillExist(@Bind("name") String name);
}
