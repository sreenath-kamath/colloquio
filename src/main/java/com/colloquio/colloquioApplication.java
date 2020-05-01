package com.colloquio;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class colloquioApplication extends Application<colloquioConfiguration> {

    public static void main(final String[] args) throws Exception {
        new colloquioApplication().run(args);
    }

    @Override
    public String getName() {
        return "colloquio";
    }

    @Override
    public void initialize(final Bootstrap<colloquioConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final colloquioConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
