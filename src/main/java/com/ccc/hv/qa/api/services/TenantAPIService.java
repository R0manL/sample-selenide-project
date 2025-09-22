package com.ccc.hv.qa.api.services;

import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.api.services.NukacolaAPIService.getNukacolaAPIService;

/**
 * Created by R0manL on 30/10/20.
 */

public class TenantAPIService {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());


    private TenantAPIService() {
        // NONE
    }

    public static TenantAPIService getTenantAPIService() {
        return new TenantAPIService();
    }

    public void deleteTenantBy(@NotNull String alphaCode) {
        log.debug("Delete tenant with alpha code: " + alphaCode);

        getNukacolaAPIService().destroyEntityBy(alphaCode);
    }
}