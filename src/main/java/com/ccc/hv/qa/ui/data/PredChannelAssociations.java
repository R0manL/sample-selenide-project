package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.ChannelAssociation;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.data.PredAddresses.AQA_CONTACT_ADDR;

/**
 * Created by R0manL on 02/09/20.
 */

public class PredChannelAssociations {
    private static final String NOTIFICATION_EMAIL = ENV_CONFIG.notificationEmail();

    public static final ChannelAssociation PRED_CHANNEL_ASSOCIATIONS_FTP = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .distributionRule(PredDistributionRules.PRED_DISTRIBUTION_RULES_1)
            .associationServer(PredAssociationSrvs.PRED_ASSOCIATION_SRV_FTP)
            .contactInformation(AQA_CONTACT_ADDR)
            .distributionNotificationRecipient(NOTIFICATION_EMAIL)
            .takeDownNotificationRecipient(NOTIFICATION_EMAIL)
            .comment("This is FTP server association comment.")
            .build();

    public static final ChannelAssociation PRED_CHANNEL_ASSOCIATIONS_SFTP = ChannelAssociation.builder()
            .automatedDistributionRules(true)
            .distributionRule(PredDistributionRules.PRED_DISTRIBUTION_RULES_2)
            .associationServer(PredAssociationSrvs.PRED_ASSOCIATION_SRV_SFTP)
            .build();

    public static final ChannelAssociation PRED_CHANNEL_ASSOCIATIONS_FTPS = ChannelAssociation.builder()
            .associationServer(PredAssociationSrvs.PRED_ASSOCIATION_SRV_FTPS)
            .build();

    public static final ChannelAssociation PRED_CHANNEL_ASSOCIATIONS_ITMS = ChannelAssociation.builder()
            .automatedDistributionRules(false)
            .associationServer(PredAssociationSrvs.PRED_ASSOCIATION_SRV_ITMS)
            .build();


    private PredChannelAssociations() {
        // NONE
    }
}
