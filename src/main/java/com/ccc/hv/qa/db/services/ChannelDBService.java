package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.ChannelDAO;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;
import static com.ccc.hv.qa.db.services.TenantDBService.getTenantIDBy;

/**
 * Created by R0manL on 02/09/20.
 */

public class ChannelDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private ChannelDBService() {
        //NONE
    }

    @NotNull("Can't find channel in DB by name.")
    public static String getChannelIDBy(@NotNull String name) {
        log.debug("Getting '" + name + "' channel's ID.");

        return createConnection()
                .onDemand(ChannelDAO.class)
                .getChannelIDBy(name);
    }

    public static int getChannelIDBy(int caId) {
        log.debug("Getting channel id by CA ID: '" + caId + "'.");
        return createConnection()
                .onDemand(ChannelDAO.class)
                .getChannelIDBy(caId);
    }

    @NotNull("Can't find channel in DB by ID.")
    public static String getChannelNameBy(int id) {
        log.debug("Getting channel's name by ID: '" + id + "'.");

        return createConnection()
                .onDemand(ChannelDAO.class)
                .getChannelNameBy(id);
    }

    public static List<Integer> getAllActiveUnlockedTestChannels() {
        log.debug("Getting all active and unlocked test channels.");
        return getAllActiveUnlockedChannelsFor(ENV_CONFIG.testBusinessUnitName());
    }

    public static List<Integer> getIDsOfChannelsWith(@NotNull String prefix) {
        log.debug("Getting list of channels' IDs with '" + prefix + "' prefix.");

        return createConnection()
                .onDemand(ChannelDAO.class)
                .getChannelsIdWith(prefix);
    }

    public static boolean hasChannelExistWith(int channelId) {
        log.debug("Check if '" + channelId + "' channel exists.");

        return createConnection()
                .onDemand(ChannelDAO.class)
                .hasChannelExistWith(channelId);
    }

    public static boolean hasChannelExistWith(@NotNull String channelName) {
        log.debug("Check if '" + channelName + "' channel exists.");

        return createConnection()
                .onDemand(ChannelDAO.class)
                .hasChannelExistWith(channelName);
    }

    public static int getChannelRetryInterval(@NotNull String name) {
        log.debug("Getting '" + name + "' channel's retry interval hours.");
        Integer channelRetryInterval = createConnection()
                .onDemand(ChannelDAO.class)
                .getRetryIntervalHours(name);
        Objects.requireNonNull(channelRetryInterval, "Can't find retry interval hours in DB for channel" + name);

        return channelRetryInterval;
    }

    public static List<Integer> getAllActiveUnlockedChannelsFor(@NotNull String tenantName) {
        log.debug("Getting all channels for BU: '" + tenantName + "'.");
        int tenantId = getTenantIDBy(tenantName);

        return createConnection()
                .onDemand(ChannelDAO.class)
                .getAllActiveUnlockedChannelsFor(tenantId);
    }
}
