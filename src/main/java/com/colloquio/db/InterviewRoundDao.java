package com.colloquio.db;

import com.colloquio.core.Candidate;
import com.colloquio.core.InterviewRound;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(InterviewRound.class)
public interface InterviewRoundDao {

    @SqlQuery("insert into interview_rounds(name, description, created_at, updated_at) values(:name, :description, now(), now()) RETURNING id")
    long createInterviewRound(@BindBean final InterviewRound interviewRound);

    @SqlQuery("select count(*) from interview_rounds where name = :name or description = :description")
    long doesInterviewRoundExist(@BindBean final InterviewRound interviewRound);

    @SqlQuery("select name, description from interview_rounds")
    List<InterviewRound> getAllInterviewRounds();

    @SqlQuery("select name, description from interview_rounds where id = :id")
    Optional<InterviewRound> findInterviewRoundById(@Bind("id") Long id);
}
