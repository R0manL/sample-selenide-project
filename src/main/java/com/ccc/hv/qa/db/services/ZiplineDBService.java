package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.ZiplineDAO;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.ProductDBService.getProductIDBy;
import static com.ccc.hv.qa.db.services.ZiplineDBConnection.createConnection;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;


public class ZiplineDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private ZiplineDBService() {
        //NONE
    }

    public static int getZiplineJobMasterIdWhenExistsFor(@NotNull String productTitle) {
        log.debug("Get job master id for '" + productTitle + "' product.");
        int productId = getProductIDBy(productTitle);
        await().atMost(ENV_CONFIG.awaitilityTimeout() * 10, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(isZiplineJobMasterIdExistFor(productId))
                        .as("Product '" + productTitle + "' has not appeared in Zipline DB.")
                        .isTrue());

        return createConnection()
                .onDemand(ZiplineDAO.class)
                .getZiplineJobMasterId(productId);
    }

    private static boolean isZiplineJobMasterIdExistFor(int productId) {
        log.debug("Check if job exists for '" + productId + "' product.");

        return createConnection()
                .onDemand(ZiplineDAO.class)
                .isZiplineJobExistFor(productId);
    }
}
