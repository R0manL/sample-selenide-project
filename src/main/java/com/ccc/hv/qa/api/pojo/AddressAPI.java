package com.ccc.hv.qa.api.pojo;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by R0manL on 04/08/20.
 */

@Getter
@Builder
public class AddressAPI {
    private final String contactFirstName;
    private final String contactLastName;
    private final String contactTitle;
    private final String email;
    private final String phoneNumber;
    private final String address1;
    private final String address2;
    private final String city;
    private final String state;
    private final String postalCode;
}
