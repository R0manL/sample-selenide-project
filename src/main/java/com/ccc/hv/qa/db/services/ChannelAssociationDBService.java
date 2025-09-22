package com.ccc.hv.qa.db.services;

import com.ccc.hv.qa.db.dao.ChannelAssociationDAO;
import com.ccc.hv.qa.db.pojo.ChannelAssociationDetails;
import com.ccc.hv.qa.logging.AllureLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;

import static com.ccc.hv.qa.db.services.HrvDBConnection.createConnection;


/**
 * Created by R0manL on 21/08/20.
 */

public class ChannelAssociationDBService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private ChannelAssociationDBService() {
        //NONE
    }

    public static int getCAServerIDBy(@NotNull String channelName) {
        log.debug("Get channel server association's id for '" + channelName + "' channel");

        return createConnection()
                .onDemand(ChannelAssociationDAO.class)
                .getChannelServerAssocIDBy(channelName);
    }

    public static int getChannelAssociationIDByServerId(int chnlServerAssociationId) {
        log.debug("Get channel server association's id for '" + chnlServerAssociationId + "' channel");

        return createConnection()
                .onDemand(ChannelAssociationDAO.class)
                .getChannelAssociationIDByServerId(chnlServerAssociationId);
    }

    @Nullable
    public static Integer getCAIDBy(int channelId) {
        log.debug("Get channel association's id for channel: '" + channelId + "'.");

        return createConnection()
                .onDemand(ChannelAssociationDAO.class)
                .getChannelAssociationIDBy(channelId);
    }

    public static boolean isCAExists(int caId) {
        log.debug("Check if CA with ID: '" + caId + "' exist.");
        return createConnection().onDemand(ChannelAssociationDAO.class).isCAExists(caId) == 1;
    }

    public static int getCAIDBy(@NotNull String channelName) {
        log.debug("Get channel association's id for channel: '" + channelName + "'.");

        return createConnection()
                .onDemand(ChannelAssociationDAO.class)
                .getChannelAssociationIDBy(channelName);
    }

    @NotNull
    public static ChannelAssociationDetails getChannelAssociationDetailsFor(@NotNull String channelName) {
        log.debug("Get association details for channel: '" + channelName + "'.");
        return createConnection()
                .onDemand(ChannelAssociationDAO.class)
                .getChannelAssociationDetailsFor(channelName);
    }
}
