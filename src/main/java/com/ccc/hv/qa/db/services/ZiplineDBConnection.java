package com.ccc.hv.qa.db.services;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;

public interface ZiplineDBConnection {

    static Jdbi createConnection() {
        return Jdbi
                .create(ENV_CONFIG.ziplineDBUrl(), ENV_CONFIG.ziplineDBUsername(), ENV_CONFIG.ziplineDBPassword())
                .installPlugin(new SqlObjectPlugin())
                .installPlugin(new PostgresPlugin());
    }
}
