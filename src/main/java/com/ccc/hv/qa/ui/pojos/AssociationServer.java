package com.ccc.hv.qa.ui.pojos;

import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Created by R0manL on 25/08/20.
 * Distribution server POJO
 */

@Getter
@SuperBuilder
public class AssociationServer {
    @NonNull
    private final String name;
    @NonNull
    private final String username;
    @NonNull
    private final String password;
    @Singular
    private final List<ChannelAssociationProductType> caProductTypes;
}
