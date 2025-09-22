package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.ProductTypeDAO;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.Map;

import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;

public class ProductTypeDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private ProductTypeDBService() {
        //NONE
    }

    public static int getProductTypeIDBy(@NotNull String name) {
        log.debug("Getting product type's id by '" + name + "' name.");
        return createConnection()
                .onDemand(ProductTypeDAO.class)
                .getProductTypeIdBy(name);
    }

    @NotNull
    public static String getProductTypeNameBy(int id) {
        log.debug("Getting product type's name by '" + id + "' id.");
        return createConnection()
                .onDemand(ProductTypeDAO.class)
                .getProductTypeNameBy(id);
    }

    public static Map<Integer, String> getAllProductTypeNames() {
        log.debug("Getting all product type names.");
        return createConnection()
                .onDemand(ProductTypeDAO.class)
                .getAllProductTypes();
    }
}
