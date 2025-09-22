package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.enums.ChannelThreshold;
import com.ccc.hv.qa.ui.enums.Headquarter;
import com.ccc.hv.qa.ui.enums.RetryInterval;
import com.ccc.hv.qa.ui.enums.TimeZone;
import com.ccc.hv.qa.ui.pojos.BusinessUnit;
import com.ccc.hv.qa.ui.pojos.ChannelPublic;

import static com.ccc.hv.qa.ui.data.PredAddresses.AQA_CONTACT_ADDR;
import static com.ccc.hv.qa.ui.data.PredDistributionSrvs.*;

/**
 * Created by R0manL on 04/08/20.
 */

public class PredChannels {
    public static final BusinessUnit BU = PredBUs.PRED_AUTOMATION_BU;


    public static final ChannelPublic PRED_CHANNEL_FTP = ChannelPublic.builder()
            .name("Predefined FTP Channel")
            .headquarter(Headquarter.AD)
            .timeZone(TimeZone.UTC)
            .retryInterval(RetryInterval.FOUR)
            .threshold(ChannelThreshold.ONE_HUNDRED)
            .exclusiveBusinessUnit(BU)
            .distributionServer(PRED_DISTRIBUTION_SRV_FTP)
            .addContactInformation(true)
            .contactInformation(AQA_CONTACT_ADDR)
            .build();

    public static final ChannelPublic PRED_CHANNEL_SFTP = ChannelPublic.builder()
            .name("Predefined SFTP Channel")
            .headquarter(Headquarter.AD)
            .timeZone(TimeZone.UTC)
            .retryInterval(RetryInterval.FOUR)
            .threshold(ChannelThreshold.ONE_HUNDRED)
            .exclusiveBusinessUnit(BU)
            .distributionServer(PRED_DISTRIBUTION_SRV_SFTP)
            .addContactInformation(true)
            .contactInformation(AQA_CONTACT_ADDR)
            .build();

    public static final ChannelPublic PRED_CHANNEL_FTPS = ChannelPublic.builder()
            .name("Predefined FTPS Channel")
            .headquarter(Headquarter.AD)
            .timeZone(TimeZone.UTC)
            .retryInterval(RetryInterval.FOUR)
            .threshold(ChannelThreshold.ONE_HUNDRED)
            .exclusiveBusinessUnit(BU)
            .distributionServer(PRED_DISTRIBUTION_SRV_FTPS)
            .addContactInformation(true)
            .contactInformation(AQA_CONTACT_ADDR)
            .build();

    public static final ChannelPublic PRED_CHANNEL_ITMS = ChannelPublic.builder()
            .name("Predefined Apple Channel")
            .headquarter(Headquarter.AD)
            .timeZone(TimeZone.UTC)
            .retryInterval(RetryInterval.FOUR)
            .threshold(ChannelThreshold.ONE_HUNDRED)
            .exclusiveBusinessUnit(BU)
            .distributionServer(PRED_DISTRIBUTION_SRV_ITMS)
            .addContactInformation(true)
            .contactInformation(AQA_CONTACT_ADDR)
            .build();


    private PredChannels() {
        //NONE
    }
}