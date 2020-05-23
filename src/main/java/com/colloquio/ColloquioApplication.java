package com.colloquio;

import com.colloquio.db.SkillsDao;
import com.colloquio.resources.AboutResource;
import com.colloquio.resources.InterviewsResource;
import com.colloquio.resources.SkillsResource;
import io.dropwizard.Application;
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

        final AboutResource aboutResource = new AboutResource(
            configuration.getOrganisation().getName()
        );
        final InterviewsResource  interviewsResource = new InterviewsResource();
        final SkillsResource skillsResource = new SkillsResource(skillsDao);
        environment.jersey().register(aboutResource);
        environment.jersey().register(interviewsResource);
        environment.jersey().register(skillsResource);
    }

}
