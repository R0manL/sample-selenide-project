package com.ccc.hv.qa.ui.pojos;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Created by R0manL on 09/01/20.
 */

@Getter
@SuperBuilder
public class AssociationServerWithMetadataDistrOpt extends AssociationServer {
    private final MetadataDistributionOption metadataDistributionOption;
}
