package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.DistributionServerMode;
import com.ccc.hv.qa.ui.enums.DistributionServerSecurity;
import com.ccc.hv.qa.ui.enums.Protocol;
import com.ccc.hv.qa.ui.pojos.DistributionServerFTP;
import com.ccc.hv.qa.ui.pojos.DistributionServerFTPS;
import com.ccc.hv.qa.ui.pojos.DistributionServerITMS;
import com.ccc.hv.qa.ui.pojos.DistributionServerSFTP;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;


/**
 * Created by R0manL on 04/08/20.
 */

public class PredDistributionSrvs {
    private static final String PRED_CRUSHFTP_HOSTNAME = ENV_CONFIG.crushFtpHostName();

    public static final DistributionServerFTP PRED_DISTRIBUTION_SRV_FTP = DistributionServerFTP.builder()
            .name("Predefined FTP server")
            .protocol(Protocol.FTP)
            .mode(DistributionServerMode.PASSIVE)
            .port(ENV_CONFIG.crushFtpFTPPort())
            .hostName(PRED_CRUSHFTP_HOSTNAME)
            .build();

    public static final DistributionServerSFTP PRED_DISTRIBUTION_SRV_SFTP = DistributionServerSFTP.builder()
            .name("Predefined SFTP server")
            .protocol(Protocol.SFTP)
            .port(ENV_CONFIG.crushFtpSFTPPort())
            .hostName(PRED_CRUSHFTP_HOSTNAME)
            .build();

    public static final DistributionServerFTPS PRED_DISTRIBUTION_SRV_FTPS = DistributionServerFTPS.builder()
            .name("Predefined FTPS server")
            .protocol(Protocol.FTPS)
            .mode(DistributionServerMode.PASSIVE)
            .security(DistributionServerSecurity.IMPLICIT)
            .port(ENV_CONFIG.crushFtpFTPSPort())
            .hostName(PRED_CRUSHFTP_HOSTNAME)
            .build();

    public static final DistributionServerITMS PRED_DISTRIBUTION_SRV_ITMS = DistributionServerITMS.builder()
            .name("Automation ITMS (Apple) server")
            .protocol(Protocol.ITMS)
            .build();


    private PredDistributionSrvs() {
        //NONE
    }
}