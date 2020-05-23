package com.colloquio.resources;

import com.codahale.metrics.annotation.Timed;
import com.colloquio.core.Skills;
import com.colloquio.db.SkillsDao;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
    public Skills getSkills(
            @QueryParam("id") @NotNull Long skillId
    ) {
        Skills skills = skillsDao.findSkillById(skillId);
        return skills;
    }
}