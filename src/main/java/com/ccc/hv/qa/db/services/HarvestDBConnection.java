package com.ccc.hv.qa.db.services;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;

/**
 * Created by R0manL on 21/08/20.
 */

public interface HrvDBConnection {

    static Jdbi createConnection() {
        return Jdbi
                .create(ENV_CONFIG.hrvDBUrl(), ENV_CONFIG.hrvDBUsername(), ENV_CONFIG.hrvDBPassword())
                .installPlugin(new SqlObjectPlugin())
                .installPlugin(new PostgresPlugin());
    }

}
