package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.*;
import com.ccc.hv.qa.ui.enums.*;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by R0manL on 25/08/20.
 */

@Getter
public class Channel {
    @NonNull
    private final String name;
    private final Headquarter headquarter;
    private final List<Headquarter> channelMarkets;
    private final TimeZone timeZone;
    private final RetryInterval retryInterval;
    private final ChannelThreshold threshold;
    private final String proprietarySchemeName;
    private final String proprietarySalesOutletID;
    private final List<DistributionServer> distributionServers;
    private final List<LocalDate> blackoutDates;
    private final boolean addContactInformation;
    private final Address contactInformation;
    private final String comment;

    protected Channel(ChannelBuilder<?, ?> b) {
        this.name = b.name;
        this.headquarter = b.headquarter;
        List<Headquarter> channelMarkets;
        switch (b.channelMarkets == null ? 0 : b.channelMarkets.size()) {
            case 0:
                channelMarkets = java.util.Collections.emptyList();
                break;
            case 1:
                channelMarkets = java.util.Collections.singletonList(b.channelMarkets.get(0));
                break;
            default:
                channelMarkets = java.util.Collections.unmodifiableList(new ArrayList<Headquarter>(b.channelMarkets));
        }
        this.channelMarkets = channelMarkets;
        this.timeZone = b.timeZone;
        this.retryInterval = b.retryInterval;
        this.threshold = b.threshold;
        this.proprietarySchemeName = b.proprietarySchemeName;
        this.proprietarySalesOutletID = b.proprietarySalesOutletID;
        List<DistributionServer> distributionServers;
        switch (b.distributionServers == null ? 0 : b.distributionServers.size()) {
            case 0:
                distributionServers = java.util.Collections.emptyList();
                break;
            case 1:
                distributionServers = java.util.Collections.singletonList(b.distributionServers.get(0));
                break;
            default:
                distributionServers = java.util.Collections.unmodifiableList(new ArrayList<DistributionServer>(b.distributionServers));
        }
        this.distributionServers = distributionServers;
        List<LocalDate> blackoutDates;
        switch (b.blackoutDates == null ? 0 : b.blackoutDates.size()) {
            case 0:
                blackoutDates = java.util.Collections.emptyList();
                break;
            case 1:
                blackoutDates = java.util.Collections.singletonList(b.blackoutDates.get(0));
                break;
            default:
                blackoutDates = java.util.Collections.unmodifiableList(new ArrayList<LocalDate>(b.blackoutDates));
        }
        this.blackoutDates = blackoutDates;
        this.addContactInformation = b.addContactInformation;
        this.contactInformation = b.contactInformation;
        this.comment = b.comment;
    }

    public static ChannelBuilder<?, ?> builder() {
        return new ChannelBuilderImpl();
    }

    public static abstract class ChannelBuilder<C extends Channel, B extends ChannelBuilder<C, B>> {
        private @NonNull String name;
        private Headquarter headquarter;
        private ArrayList<Headquarter> channelMarkets;
        private TimeZone timeZone;
        private RetryInterval retryInterval;
        private ChannelThreshold threshold;
        private String proprietarySchemeName;
        private String proprietarySalesOutletID;
        private ArrayList<DistributionServer> distributionServers;
        private ArrayList<LocalDate> blackoutDates;
        private boolean addContactInformation;
        private Address contactInformation;
        private String comment;

        public B name(@NonNull String name) {
            if(!TestPrefix.isStartWithTestPrefix(name)) {
                throw new IllegalArgumentException("Channel's name: '" + name + "' must start with test prefix. " +
                        "Note: it's required for test channels cleanup.");
            }

            final int MAX_CHANNEL_NAME_LENGTH = 64;
            if(name.length() > MAX_CHANNEL_NAME_LENGTH) {
                throw new IllegalArgumentException("Channel's name: '" + name + "' is too long. " +
                        "Max length = " + MAX_CHANNEL_NAME_LENGTH + ", current = " + name.length() + ".");
            }

            this.name = name;

            return self();
        }

        public B headquarter(Headquarter headquarter) {
            this.headquarter = headquarter;
            return self();
        }

        public B channelMarket(Headquarter channelMarket) {
            if (this.channelMarkets == null) this.channelMarkets = new ArrayList<Headquarter>();
            this.channelMarkets.add(channelMarket);
            return self();
        }

        public B channelMarkets(Collection<? extends Headquarter> channelMarkets) {
            if (this.channelMarkets == null) this.channelMarkets = new ArrayList<Headquarter>();
            this.channelMarkets.addAll(channelMarkets);
            return self();
        }

        public B clearChannelMarkets() {
            if (this.channelMarkets != null)
                this.channelMarkets.clear();
            return self();
        }

        public B timeZone(TimeZone timeZone) {
            this.timeZone = timeZone;
            return self();
        }

        public B retryInterval(RetryInterval retryInterval) {
            this.retryInterval = retryInterval;
            return self();
        }

        public B threshold(ChannelThreshold threshold) {
            this.threshold = threshold;
            return self();
        }

        public B proprietarySchemeName(String proprietarySchemeName) {
            this.proprietarySchemeName = proprietarySchemeName;
            return self();
        }

        public B proprietarySalesOutletID(String proprietarySalesOutletID) {
            this.proprietarySalesOutletID = proprietarySalesOutletID;
            return self();
        }

        public B distributionServer(DistributionServer distributionServer) {
            if (this.distributionServers == null) this.distributionServers = new ArrayList<DistributionServer>();
            this.distributionServers.add(distributionServer);
            return self();
        }

        public B distributionServers(Collection<? extends DistributionServer> distributionServers) {
            if (this.distributionServers == null) this.distributionServers = new ArrayList<DistributionServer>();
            this.distributionServers.addAll(distributionServers);
            return self();
        }

        public B clearDistributionServers() {
            if (this.distributionServers != null)
                this.distributionServers.clear();
            return self();
        }

        public B blackoutDate(LocalDate blackoutDate) {
            if (this.blackoutDates == null) this.blackoutDates = new ArrayList<LocalDate>();
            this.blackoutDates.add(blackoutDate);
            return self();
        }

        public B blackoutDates(Collection<? extends LocalDate> blackoutDates) {
            if (this.blackoutDates == null) this.blackoutDates = new ArrayList<LocalDate>();
            this.blackoutDates.addAll(blackoutDates);
            return self();
        }

        public B clearBlackoutDates() {
            if (this.blackoutDates != null)
                this.blackoutDates.clear();
            return self();
        }

        public B addContactInformation(boolean addContactInformation) {
            this.addContactInformation = addContactInformation;
            return self();
        }

        public B contactInformation(Address contactInformation) {
            this.contactInformation = contactInformation;
            return self();
        }

        public B comment(String comment) {
            this.comment = comment;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "Channel.ChannelBuilder(name=" + this.name + ", headquarter=" + this.headquarter + ", channelMarkets=" + this.channelMarkets + ", timeZone=" + this.timeZone + ", retryInterval=" + this.retryInterval + ", threshold=" + this.threshold + ", proprietarySchemeName=" + this.proprietarySchemeName + ", proprietarySalesOutletID=" + this.proprietarySalesOutletID + ", distributionServers=" + this.distributionServers + ", blackoutDates=" + this.blackoutDates + ", addContactInformation=" + this.addContactInformation + ", contactInformation=" + this.contactInformation + ", comment=" + this.comment + ")";
        }
    }

    private static final class ChannelBuilderImpl extends ChannelBuilder<Channel, ChannelBuilderImpl> {
        private ChannelBuilderImpl() {
        }

        protected ChannelBuilderImpl self() {
            return this;
        }

        public Channel build() {
            return new Channel(this);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
