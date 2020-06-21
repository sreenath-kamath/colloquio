package com.colloquio.db;

import com.colloquio.core.Candidate;
import com.colloquio.core.Skills;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(Candidate.class)
public interface CandidatesDao {

    @SqlQuery("insert into candidates(full_name, email,  phone_number, created_at, updated_at) values(:fullName, :email, :phoneNumber, now(), now()) RETURNING id")
    long createCandidate(@BindBean final Candidate candidate);

    @SqlQuery("select count(*) from candidates where email = :email or phone_number = :phoneNumber")
    long doesCandidateExist(@BindBean final Candidate candidate);

    @SqlQuery("select email, phone_number, full_name from candidates")
    List<Candidate> getAllCandidates();
}
