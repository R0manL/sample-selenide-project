package com.ccc.hv.qa.ui.services;

import com.ccc.hv.qa.ui.enums.AssetType;
import com.ccc.hv.qa.ui.enums.RetryInterval;
import com.ccc.hv.qa.ui.pojos.*;
import com.ccc.hv.qa.ui.pojos.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

import static com.ccc.hv.qa.ui.pages.TopMenu.getTopMenu;

/**
 * Created by R0manL on 02/09/21.
 */

public class ChannelAndCATestService {
    private static final int DEFAULT_RETRY_INTERVAL  = RetryInterval.FOUR.getOptionValue();
    private final Channel channel;
    private final ChannelAssociation ca;


    public ChannelAndCATestService(@NotNull Channel channel, @NotNull ChannelAssociation channelAssociation) {
        this.channel = channel;
        this.ca = channelAssociation;
    }

    @NotNull
    public String getChannelName() {
        return channel.getName();
    }

    public ChannelAndCATestService createChannelWithCA() {
        getTopMenu()
                .clickAddChannelWithJS()
                .createChannel(this.channel);

        getTopMenu()
                .clickManageChannel()
                .openAddChannelAssociationsPageFor(this.channel.getName())
                .createChannelAssociation(this.ca)
                .clickSave()
                .isSuccessMsgVisible()
                .returnToAssociatedChannelsList();

        return this;
    }

    public int getChannelRetryInterval() {
        RetryInterval channelRetryInterval = this.channel.getRetryInterval();

        return channelRetryInterval != null ? channelRetryInterval.getOptionValue() : DEFAULT_RETRY_INTERVAL;
    }

    public AssetType getAssetTypeFromSingleContent() {
        return AssetType.from(getSingleContent().getContentType());
    }

    public AssetType getAssetTypeFromSingleCollateral() {
        return AssetType.from(getSingleCollateral().getCollateralType());
    }

    public AssetType getAssetTypeFromSingleMetadata() {
        return AssetType.from(getSingleMetadata().getMetadataType());
    }

    @NotNull
    public AssociationServer getSingleAssociationServer() {
        return getSingleElmFrom(this.ca.getAssociationServers());
    }

    @NotNull
    public ChannelAssociationProductType getSingleProduct() {
        return getSingleElmFrom(getSingleAssociationServer().getCaProductTypes());
    }

    @NotNull
    public ChannelAssociationContent getSingleContent() {
        return getSingleElmFrom(getSingleProduct().getContents());
    }

    @NotNull
    public ChannelAssociationCollateral getSingleCollateral() {
        return getSingleElmFrom(getSingleProduct().getCollaterals());
    }

    @NotNull
    public ChannelAssociationMetadata getSingleMetadata() {
        return getSingleElmFrom(getSingleProduct().getMetadatas());
    }

    @NotNull
    public Set<ChannelAssociationMetadata> getMultipleMetadata() {
        return getSingleProduct().getMetadatas();
    }

    public DistributionRule getDistributionRule() {
        return this.ca.getDistributionRule();
    }

    @NotNull
    private <T> T getSingleElmFrom(Collection<T> list) {
        if (list.size() == 1) { return list.stream().findFirst().get(); }

        throw new IllegalArgumentException("Expect single value in the list, but '" + list.size() + "' found.");
    }
}
