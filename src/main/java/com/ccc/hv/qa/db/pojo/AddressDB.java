package com.ccc.hv.qa.db.pojo;

import lombok.Value;

/**
 * Created by R0manL on 1/20/21.
 */

@Value
public class AddressDB {
    int hvAddmasAddressId;
    String contactFirstName;
    String contactLastName;
    String contactTitle;
    String phoneNumber;
    String email;
    String address1;
    String address2;
    String address3;
    String address4;
    String city;
    String state;
    String postalCode;
    String country;
    boolean activeYn;
    String createDateUtc;
    String addressType;
}
