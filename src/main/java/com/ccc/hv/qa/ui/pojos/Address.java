package com.ccc.hv.qa.ui.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Created by R0manL on 04/08/20.
 */

@Getter
@Builder
public class Address {
    @NonNull
    private final String firstName;
    @NonNull
    private final String lastName;
    @NonNull
    private final String contactTitle;
    @NonNull
    private final String email;
    @NonNull
    private final String phoneNumber;
    @NonNull
    private final String addressOne;
    private final String addressTwo;
    @NonNull
    private final String city;
    @NonNull
    private final String state;
    @NonNull
    private final String postalCode;
    private final String country;
}
