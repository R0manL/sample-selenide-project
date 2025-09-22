package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.AssociationServerFTP;
import com.ccc.hv.qa.ui.pojos.AssociationServerFTPS;
import com.ccc.hv.qa.ui.pojos.AssociationServerITMS;
import com.ccc.hv.qa.ui.pojos.AssociationServerSFTP;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.ui.data.PredChannelAssociationProductTypes.*;
import static com.ccc.hv.qa.ui.data.PredDistributionSrvs.*;
import static com.ccc.hv.qa.ui.data.PredMetadataDistributionOptions.PRED_METADATA_DISTR_OPTIONS_WITH_GROUP_BY_PUBLISHER_AND_IMPRINTENABLED;
import static com.ccc.hv.qa.ui.data.PredMetadataDistributionOptions.PRED_METADATA_DISTR_OPTIONS_WITH_SEND_METADATA_WITH_EVERY_ASSETENABLED;

/**
 * Created by R0manL on 02/09/20.
 */

public class PredAssociationSrvs {

    public static final AssociationServerFTP PRED_ASSOCIATION_SRV_FTP = AssociationServerFTP.builder()
            .name(PRED_DISTRIBUTION_SRV_FTP.getName())
            .username(ENV_CONFIG.associationServerFTPUsername())
            .password(ENV_CONFIG.associationServerFTPPassword())
            .metadataDistributionOption(PRED_METADATA_DISTR_OPTIONS_WITH_GROUP_BY_PUBLISHER_AND_IMPRINTENABLED)
            .caProductType(PRED_CHANNEL_ASSOCIATION_PRODUCT_TYPE_FTP)
            .build();

    public static final AssociationServerSFTP PRED_ASSOCIATION_SRV_SFTP = AssociationServerSFTP.builder()
            .name(PRED_DISTRIBUTION_SRV_SFTP.getName())
            .username(ENV_CONFIG.associationServerSFTPUsername())
            .password(ENV_CONFIG.associationServerSFTPPassword())
            .metadataDistributionOption(PRED_METADATA_DISTR_OPTIONS_WITH_SEND_METADATA_WITH_EVERY_ASSETENABLED)
            .caProductType(PRED_CHANNEL_ASSOCIATION_PRODUCT_TYPE_SFTP)
            .build();

    public static final AssociationServerFTPS PRED_ASSOCIATION_SRV_FTPS = AssociationServerFTPS.builder()
            .name(PRED_DISTRIBUTION_SRV_FTPS.getName())
            .username(ENV_CONFIG.associationServerFTPSUsername())
            .password(ENV_CONFIG.associationServerFTPSPassword())
            .caProductType(PRED_CHANNEL_ASSOCIATION_PRODUCT_TYPE_FTPS)
            .build();

    public static final AssociationServerITMS PRED_ASSOCIATION_SRV_ITMS = AssociationServerITMS.builder()
            .name(PRED_DISTRIBUTION_SRV_ITMS.getName())
            .username(ENV_CONFIG.associationServerITMSUsername())
            .password(ENV_CONFIG.associationServerITMSPassword())
            .provider(ENV_CONFIG.associationServerITMSProvider())
            .caProductType(PRED_CHANNEL_ASSOCIATION_PRODUCT_TYPE_ITMS)
            .build();


    private PredAssociationSrvs() {
        // NONE
    }
}
