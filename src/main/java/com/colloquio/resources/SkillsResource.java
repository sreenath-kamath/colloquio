package com.colloquio.resources;

import com.codahale.metrics.annotation.Timed;
import com.colloquio.api.Info;
import com.colloquio.core.Skills;
import com.colloquio.db.SkillsDao;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("metadata/skills")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SkillsResource {

    private SkillsDao skillsDao;

    public SkillsResource(SkillsDao skillsDao){
        this.skillsDao = skillsDao;
    }

    @GET
    @Timed
    @PermitAll
    public List<Skills> getSkills(){
        return skillsDao.getSkills();
    }


    @GET
    @Timed
    @PermitAll
    @Path("{id}")
    public Skills getSkills(
            @PathParam("id") @NotNull Long skillId
    ) {
        Skills skills = skillsDao.findSkillById(skillId);
        return skills;
    }

    @POST
    @Timed
    @PermitAll
    public Info createSkill(
            Skills skills
    ){
        long createdSkillId = skillsDao.createSkill(skills);
        return new Info(createdSkillId, skills.getName());
    }
}
