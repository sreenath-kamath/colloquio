package com.colloquio;

import com.colloquio.auth.ColloquioBasicAuthenticator;
import com.colloquio.core.InterviewRound;
import com.colloquio.core.User;
import com.colloquio.db.CandidatesDao;
import com.colloquio.db.InterviewRoundDao;
import com.colloquio.db.SkillsDao;
import com.colloquio.db.UserDao;
import com.colloquio.resources.AboutResource;
import com.colloquio.resources.CandidatesResource;
import com.colloquio.resources.InterviewRoundsResource;
import com.colloquio.resources.InterviewsResource;
import com.colloquio.resources.SkillsResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

public class ColloquioApplication extends Application<ColloquioConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ColloquioApplication().run(args);
    }

    @Override
    public String getName() {
        return "Colloquio";
    }

    @Override
    public void initialize(final Bootstrap<ColloquioConfiguration> bootstrap) {

        bootstrap.addBundle(new MigrationsBundle<ColloquioConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ColloquioConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

    }

    @Override
    public void run(final ColloquioConfiguration configuration,
                    final Environment environment) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "Colloquio DataBase");

        final SkillsDao skillsDao = jdbi.onDemand(SkillsDao.class);
        final UserDao userDao = jdbi.onDemand(UserDao.class);
        final CandidatesDao candidatesDao = jdbi.onDemand(CandidatesDao.class);
        final InterviewRoundDao interviewRoundDao = jdbi.onDemand(InterviewRoundDao.class);

        final AboutResource aboutResource = new AboutResource(
            configuration.getOrganisation().getName()
        );
        final InterviewsResource  interviewsResource = new InterviewsResource();
        final SkillsResource skillsResource = new SkillsResource(skillsDao);
        final CandidatesResource candidatesResource = new CandidatesResource(candidatesDao);
        final InterviewRoundsResource interviewRoundsResource = new InterviewRoundsResource(interviewRoundDao);
        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new ColloquioBasicAuthenticator(userDao))
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(aboutResource);
        environment.jersey().register(interviewsResource);
        environment.jersey().register(skillsResource);
        environment.jersey().register(candidatesResource);
        environment.jersey().register(interviewRoundsResource);
    }

}
