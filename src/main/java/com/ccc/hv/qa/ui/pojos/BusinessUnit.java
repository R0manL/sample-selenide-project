package com.ccc.hv.qa.ui.pojos;

import com.ccc.hv.qa.ui.enums.IngestMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Created by R0manL on 04/08/20.
 */

@Getter
@Builder
public class BusinessUnit {
    @NonNull
    private final String name;
    @NonNull
    private final String recordSourceName;
    private final String alphaCode;
    private final boolean useExpectedShipDate;
    private final IngestMode ingestMode;
    private final String distributionErrorNotificationEmail;
    private final Address profileContactInfo;
    @NonNull
    private final Address billingContactInfo;
    @NonNull
    private final String userId;
    @NonNull
    private final String password;
    private final String publisherId;
    private final String publisherPin;
    private final int imageThreshold;
    private final boolean useParentAccountInfo;

    @Override
    public String toString() {
        return name + ", " + alphaCode;
    }
}
