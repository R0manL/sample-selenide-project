package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.AddressDAO;
import com.ccc.hv.qa.db.pojo.AddressDB;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;

public class AddressDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private AddressDBService() {
        //NONE
    }

    @NotNull
    public static AddressDB getAddressBy(int id) {
        log.debug("Get address with ID: '" + id + "'.");
        return createConnection()
                .onDemand(AddressDAO.class)
                .getAddressBy(id);
    }
}