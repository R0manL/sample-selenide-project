package com.ccc.hv.qa.ui.utils;

import com.ccc.hv.qa.api.pojo.*;
import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.api.pojo.*;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.utils.enums.DatePattern;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.ccc.hv.qa.db.services.TenantDBService.getTenantIDBy;
import static com.ccc.hv.qa.utils.DateTimeUtils.changeDateFormat;

/**
 * Created by R0manL on 12/08/20.
 */

public class ChannelUtils {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private ChannelUtils() {
        // NONE
    }


    @NotNull
    public static ChannelAPI convertToChannelAPIEntity(Channel channel) {
        log.debug("Convert channel, UI > API.");

        return ChannelAPI.builder()
                .generalStep(createGeneralStepFrom(channel))
                .exclusiveStep(createExclusiveStepFrom(channel))
                .distributionStep(createDistributionStepFrom(channel))
                .serversStep(createServersStepFrom(channel.getDistributionServers()))
                .blackoutsStep(createBlackoutStepFrom(channel.getBlackoutDates()))
                .contactStep(createContactStepFrom(channel.getContactInformation()))
                .commentsStep(createCommentStepFrom(channel.getComment()))
                .build();
    }

    @NotNull
    private static GeneralStep createGeneralStepFrom(Channel channel) {
        log.debug("Create channel general step from channel.");

        if (channel instanceof ChannelPublic) {
            return GeneralStep.builder()
                    .name(channel.getName())
                    .market(((ChannelPublic) channel).getHrvViewMarketMapping())
                    .headquarters(channel.getHeadquarter() != null ? channel.getHeadquarter() : Headquarter.AD)
                    .footprint(channel.getChannelMarkets())
                    .timezone(channel.getTimeZone() != null ? channel.getTimeZone().getOptionValue() : TimeZone.ACDT.getOptionValue())
                    .retryInterval(channel.getRetryInterval() != null ? channel.getRetryInterval().toString() : RetryInterval.FOUR.toString())
                    .threshold(channel.getThreshold() != null ? channel.getThreshold().toString() : ChannelThreshold.ONE_HUNDRED.toString())
                    .build();
        }

        return GeneralStep.builder()
                .name(channel.getName())
                .headquarters(channel.getHeadquarter() != null ? channel.getHeadquarter() : Headquarter.AD)
                .footprint(channel.getChannelMarkets())
                .timezone(channel.getTimeZone() != null ? channel.getTimeZone().getOptionValue() : TimeZone.ACDT.getOptionValue())
                .retryInterval(channel.getRetryInterval() != null ? channel.getRetryInterval().toString() : RetryInterval.FOUR.toString())
                .threshold(channel.getThreshold() != null ? channel.getThreshold().toString() : ChannelThreshold.ONE_HUNDRED.toString())
                .build();
    }

    @NotNull
    private static ExclusiveStep createExclusiveStepFrom(Channel channel) {
        log.debug("Create channel exclusive step from channel.");

        return ExclusiveStep.builder()
                .exclusive(false)
                .makeBusinessUnitExclusive(true)
                .businessUnits(channel instanceof ChannelPublic ? convertToBUsAPI(((ChannelPublic) channel).getExclusiveBusinessUnits()) : new ArrayList<>())
                .build();
    }

    @NotNull
    public static DistributionStep createDistributionStepFrom(Channel channel) {
        log.debug("Create channel distribution step from channel.");

        String salesOutlet = "";
        if (channel instanceof ChannelPublic && ((ChannelPublic) channel).getOnixSalesOutletIDCode() != null) {
            salesOutlet = ((ChannelPublic) channel).getOnixSalesOutletIDCode().toString();
        }

        return DistributionStep.builder()
                .supportAdvancedKeywords(String.valueOf(channel instanceof ChannelPublic && ((ChannelPublic) channel).isDistributeByAdvancedKeyword()))
                .salesOutlet(salesOutlet)
                .propSalesOutletType(channel.getProprietarySchemeName())
                .propSalesOutlet(channel.getProprietarySalesOutletID())
                .build();
    }

    @NotNull
    public static ServersStep createServersStepFrom(List<DistributionServer> distributionServers) {
        log.debug("Create channel servers step from distribution servers.");

        return ServersStep.builder()
                .servers(convertToDistributionServersAPI(distributionServers))
                .build();
    }

    @NotNull
    public static BlackoutsStep createBlackoutStepFrom(List<LocalDate> blackoutDates) {
        log.debug("Create channel blackout step from blackout dates.");

        List<BlackoutDateAPI> blackoutDatesAPI = new ArrayList<>();
        for (LocalDate date : blackoutDates) {
            blackoutDatesAPI.add(BlackoutDateAPI.builder()
                    .date(changeDateFormat(date, DatePattern.UI_BLACKOUT))
                    .build());
        }

        return BlackoutsStep.builder()
                .dates(blackoutDatesAPI)
                .build();
    }

    @NotNull
    public static ContactStep createContactStepFrom(Address address) {
        log.debug("Create channel contact step from address.");

        AddressAPI addressAPI;
        if (address != null) {
            addressAPI = AddressAPI.builder()
                    .contactFirstName(address.getFirstName())
                    .contactLastName(address.getLastName())
                    .contactTitle(address.getContactTitle())
                    .email(address.getEmail())
                    .phoneNumber(address.getPhoneNumber())
                    .address1(address.getAddressOne())
                    .address2(address.getAddressTwo())
                    .city(address.getCity())
                    .state(address.getState())
                    .postalCode(address.getPostalCode())
                    .build();
        } else {
            addressAPI = AddressAPI.builder().build();
        }

        return ContactStep.builder()
                .address(addressAPI)
                .build();
    }

    @NotNull
    public static CommentsStep createCommentStepFrom(String comment) {
        log.debug("Create channel comment from comment (UI).");

        return CommentsStep.builder()
                .comment(comment)
                .build();
    }

    private static List<String> convertToBUsAPI(List<BusinessUnit> businessUnits) {
        log.debug("Convert BUs, UI > API.");

        List<String> result = new ArrayList<>();
        for (BusinessUnit bu : businessUnits) {
            int buId = getTenantIDBy(bu.getName());
            result.add(String.valueOf(buId));
        }

        return result;
    }

    private static List<DistributionServerAPI> convertToDistributionServersAPI(List<DistributionServer> distributionServers) {
        log.debug("Convert distribution servers, UI > API.");

        List<DistributionServerAPI> result = new ArrayList<>();
        for (DistributionServer server : distributionServers) {
            result.add(DistributionServerAPI.builder()
                    .name(server.getName())
                    .protocol(convertToDistributionProtocolAPI(server))
                    .build());
        }

        return result;
    }

    @NotNull
    private static DistributionProtocolAPI convertToDistributionProtocolAPI(@NotNull DistributionServer distributionServer) {
        log.debug("Convert distribution server to distribution protocol.");

        if (distributionServer.getProtocol() == Protocol.FTP) {
            DistributionServerFTP srv = (DistributionServerFTP) distributionServer;
            return DistributionFtpProtocolAPI.builder()
                    .type(srv.getProtocol().name().toLowerCase())
                    .mode(srv.getMode().getOptionValue())
                    .host(srv.getHostName())
                    .portString(String.valueOf(srv.getPort()))
                    .build();
        }

        if (distributionServer.getProtocol() == Protocol.SFTP) {
            DistributionServerSFTP srv = (DistributionServerSFTP) distributionServer;
            return DistributionSftpProtocolAPI.builder()
                    .type(srv.getProtocol().name().toLowerCase())
                    .host(srv.getHostName())
                    .portString(String.valueOf(srv.getPort()))
                    .build();
        }

        if (distributionServer.getProtocol() == Protocol.FTPS) {
            DistributionServerFTPS srv = (DistributionServerFTPS) distributionServer;
            return DistributionFtpsProtocolAPI.builder()
                    .type(srv.getProtocol().name().toLowerCase())
                    .mode((srv.getMode() != null ? srv.getMode() : DistributionServerMode.PASSIVE).getOptionValue())
                    .host(srv.getHostName())
                    .portString(String.valueOf(srv.getPort()))
                    .securityInvocationMethod((srv.getSecurity() != null ? srv.getSecurity() : DistributionServerSecurity.IMPLICIT).toString())
                    .build();
        }

        if (distributionServer.getProtocol() == Protocol.ITMS) {
            DistributionServerITMS srv = (DistributionServerITMS) distributionServer;
            return DistributionItmsProtocolAPI.builder()
                    .type(srv.getProtocol().name().toLowerCase())
                    .build();
        }

        throw new IllegalStateException("Can't identify distribution server type.");
    }
}
