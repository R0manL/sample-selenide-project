package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.ConfigDAO;
import com.ccc.hv.qa.logging.AllureLogger;
import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;

public class ConfigDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private ConfigDBService() {
        //NONE
    }

    public static int getMaxProductDelete() {
        log.debug("Get MAX_PRODUCT_DELETE value.");

        return createConnection()
                .onDemand(ConfigDAO.class)
                .getMaxProductDelete();
    }

    public static void setMaxProductDelete(int value) {
        log.debug("Set MAX_PRODUCT_DELETE value = '" + value + "'.");
        createConnection()
                .onDemand(ConfigDAO.class)
                .setMaxProductDelete(value);
    }
}