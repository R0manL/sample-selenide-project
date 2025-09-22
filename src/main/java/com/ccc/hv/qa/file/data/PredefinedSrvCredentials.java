package com.ccc.hv.qa.file.data;

import com.ccc.hv.qa.file.pojo.ServerCredentials;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;

/**
 * Created by R0manL on 27/08/20.
 */

public class PredefinedSrvCredentials {

    private PredefinedSrvCredentials() {
        //NONE
    }

    public static final ServerCredentials crushFtpUploadCreds = ServerCredentials.builder()
            .username(ENV_CONFIG.crushFtpUploadUsername())
            .password(ENV_CONFIG.crushFtpUploadPassword())
            .build();

    public static final ServerCredentials crushFtpUploadCreds2 = ServerCredentials.builder()
            .username(ENV_CONFIG.crushFtpUploadUsername2())
            .password(ENV_CONFIG.crushFtpUploadPassword2())
            .build();

    public static final ServerCredentials crushFtpDownloadFTPCreds = ServerCredentials.builder()
            .username(ENV_CONFIG.associationServerFTPUsername())
            .password(ENV_CONFIG.associationServerFTPPassword())
            .build();

    public static final ServerCredentials crushFtpDownloadSFTPCreds = ServerCredentials.builder()
            .username(ENV_CONFIG.associationServerSFTPUsername())
            .password(ENV_CONFIG.associationServerSFTPPassword())
            .build();

    public static final ServerCredentials crushFtpDownloadFTPSCreds = ServerCredentials.builder()
            .username(ENV_CONFIG.associationServerFTPSUsername())
            .password(ENV_CONFIG.associationServerFTPSPassword())
            .build();
}
