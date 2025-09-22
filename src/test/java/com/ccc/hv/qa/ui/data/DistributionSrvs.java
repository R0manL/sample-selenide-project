package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.DistributionServerMode;
import com.ccc.hv.qa.ui.enums.Protocol;
import com.ccc.hv.qa.ui.pojos.DistributionServerFTP;
import com.ccc.hv.qa.ui.pojos.DistributionServerFTPS;
import com.ccc.hv.qa.ui.pojos.DistributionServerITMS;
import com.ccc.hv.qa.ui.pojos.DistributionServerSFTP;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;

/**
 * Created by R0manL on 25/08/20.
 */

public class DistributionSrvs {
    private static final String UNIQUE_POSTFIX = generateUniqueStringBasedOnDate();
    private static final String DISTRIBUTION_SRV = ENV_CONFIG.crushFtpHostName();

    /* Smoke servers channels */
    public static final DistributionServerFTP smokeDistributionSrvFTP = DistributionServerFTP.builder()
            .name("Smoke_ftp_srv_" + UNIQUE_POSTFIX)
            .protocol(Protocol.FTP)
            .mode(DistributionServerMode.PASSIVE)
            .port(2121)
            .hostName(DISTRIBUTION_SRV)
            .build();

    public static final DistributionServerSFTP smokeDistributionSrvSFTP = DistributionServerSFTP.builder()
            .name("Smoke_sftp_srv_" + UNIQUE_POSTFIX)
            .protocol(Protocol.SFTP)
            .port(2222)
            .hostName(DISTRIBUTION_SRV)
            .build();

    public static final DistributionServerFTPS smokeDistributionSrvFTPS = DistributionServerFTPS.builder()
            .name("Smoke_ftps_srv_" + UNIQUE_POSTFIX)
            .protocol(Protocol.FTPS)
            .port(9990)
            .hostName(DISTRIBUTION_SRV)
            .build();

    public static final DistributionServerITMS smokeDistributionSrvITMS = DistributionServerITMS.builder()
            .name("Smoke_itms_srv_" + UNIQUE_POSTFIX)
            .protocol(Protocol.ITMS)
            .build();
    /* end of Smoke servers */

    /* Regression servers */
    public static final DistributionServerFTPS regressionInvalidDistributionSrvFTPS = DistributionServerFTPS.builder()
            .name("Regression_ftps_srv_invld_port_" + UNIQUE_POSTFIX)
            .protocol(Protocol.FTPS)
            .port(1234)
            .hostName(DISTRIBUTION_SRV)
            .build();
    /* end of Regression servers */

    private DistributionSrvs() {
        // None
    }
}
