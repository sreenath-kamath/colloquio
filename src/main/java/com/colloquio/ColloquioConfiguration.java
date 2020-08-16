package com.colloquio;

import com.colloquio.core.Organisation;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.*;

@Getter
@Setter
public class ColloquioConfiguration extends Configuration {

    @NotNull
    @JsonProperty("organisation")
    private Organisation organisation;

    @NotNull
    @JsonProperty("database")
    private DataSourceFactory dataSourceFactory;

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

}
