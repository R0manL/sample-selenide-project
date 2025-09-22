package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.PublisherDAO;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;
import static com.ccc.hv.qa.db.services.TenantDBService.getTenantIDBy;


/**
 * Created by R0manL on 21/08/20.
 */

public class PublisherDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private PublisherDBService() {
        //NONE
    }

    public static int getPublisherIDBy(@NotNull String name, @NotNull String tenantName) {
        log.debug("Getting '" + name + "' publisher's ID for tenant: '" + tenantName + "'.");
        int tenantID = getTenantIDBy(tenantName);

        return createConnection()
                .onDemand(PublisherDAO.class)
                .getPublisherIDBy(name, tenantID);
    }
}
