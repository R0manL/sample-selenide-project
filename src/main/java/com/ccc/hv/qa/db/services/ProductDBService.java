package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.ProductDAO;
import com.ccc.hv.qa.db.pojo.ProductDB;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;
import static com.ccc.hv.qa.utils.StringUtils.REVISION_PREFIX;
import static org.awaitility.Awaitility.await;

public class ProductDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private ProductDBService() {
        //NONE
    }

    public static int getProductIDBy(@NotNull String productTitle) {
        log.debug("Getting '" + productTitle + "' product's id.");

        return waitAndGetProductFromDbBy(productTitle)
                .getHvPrdmasProductMasterId();
    }

    @Nullable
    public static ProductDB getProductBy(@NotNull String productTitle) {
        log.debug("Getting product with title: '" + productTitle);

        return createConnection()
                .onDemand(ProductDAO.class)
                .getProductBy(productTitle);
    }

    public static boolean hasProductOnHold(@NotNull String productTitle) {
        log.debug("Is '" + productTitle + "' product on hold.");
        return waitAndGetProductFromDbBy(productTitle)
                .isOnHold();
    }

    public static boolean hasProductMarkedForDeletion(@NotNull String productTitle) {
        log.debug("Has '" + productTitle + "' product marked for deletion.");

        return waitAndGetProductFromDbBy(productTitle)
                .isMarkedForDeleteYn();
    }

    public static boolean isProductLocked(@NotNull String productTitle) {
        log.debug("Get locked status for '" + productTitle + "' product.");

        return waitAndGetProductFromDbBy(productTitle)
                .isEmbargoedYn();
    }

    public static void waitOnProductInDbBy(@NotNull String productTitle) {
        waitAndGetProductFromDbBy(productTitle);
    }

    @NotNull
    public static ProductDB waitAndGetProductFromDbBy(@NotNull String productTitle) {
        log.debug("Wait when '" + productTitle + "' product's record appears.");

        ProductDB[] result = new ProductDB[1];
        await().until(() -> {
            ProductDB product = getProductBy(productTitle);
            result[0] = product;
            return product != null;
        });

        return result[0];
    }

    public static int getNumOfTestProducts() {
        log.debug("Get number of test products.");

        return createConnection()
                .onDemand(ProductDAO.class)
                .getNumOfTestProducts(REVISION_PREFIX);
    }

    public static void markTestProductsForDeletion() {
        log.debug("Mark all tests products for deletion.");
        createConnection()
                .onDemand(ProductDAO.class)
                .markTestProductsForDeletion(REVISION_PREFIX);
    }
}
