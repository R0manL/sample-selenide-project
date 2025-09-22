package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.Address;

import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueFakePhoneNumber;
import static com.ccc.hv.qa.utils.StringUtils.generateUniqueStringBasedOnDate;

/**
 * Created by R0manL on 04/08/20.
 */

public class PredAddresses {
    private static final String NOTIFICATION_EMAIL = ENV_CONFIG.notificationEmail();

    public static final Address AQA_CONTACT_ADDR = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Pred AQA Contact First Name "))
            .lastName(generateUniqueStringBasedOnDate("Pred AQA Contact Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Senior AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation tester's contact address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation tester's contact address "))
            .city("Austin")
            .state("TX")
            .postalCode("73301")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_BILLING_ADDR = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Pred AQA Billing First Name "))
            .lastName(generateUniqueStringBasedOnDate("Pred AQA Billing Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Mid AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation tester's billing address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation tester's billing address "))
            .city("NewYork")
            .state("NY")
            .postalCode("10001")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_CONTACT_ADDR_TEST1 = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test1 AQA Contact First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test1 AQA Contact Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test1 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test1 contact address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test1 contact address "))
            .city("Austin")
            .state("TX")
            .postalCode("73301")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_BILLING_ADDR_TEST1 = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test1 AQA Billing First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test1 AQA Billing Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test1 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test1 billing address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test1 billing address "))
            .city("NewYork")
            .state("NY")
            .postalCode("10001")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_CONTACT_ADDR_TEST2 = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test2 AQA Contact First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test2 AQA Contact Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test2 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test2 contact address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test2 contact address "))
            .city("Austin")
            .state("TX")
            .postalCode("73301")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_BILLING_ADDR_TEST2 = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test2 AQA Billing First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test2 AQA Billing Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test2 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test2 billing address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test2 billing address "))
            .city("NewYork")
            .state("NY")
            .postalCode("10001")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_CONTACT_ADDR_TEST3 = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test3 AQA Contact First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test3 AQA Contact Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test3 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test3 contact address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test3 contact address "))
            .city("Austin")
            .state("TX")
            .postalCode("73301")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_BILLING_ADDR_TEST3 = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test3 AQA Billing First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test3 AQA Billing Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test3 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test3 billing address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test3 billing address "))
            .city("NewYork")
            .state("NY")
            .postalCode("10001")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_CONTACT_ADDR_EDIT_ACCOUNT_TEST1 = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test1 AQA Contact First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test1 AQA Contact Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test1 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test1 contact address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test1 contact address "))
            .city("Austin")
            .state("TX")
            .postalCode("73301")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_BILLING_ADDR_EDIT_ACCOUNT_TEST1 = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test1 AQA Billing First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test1 AQA Billing Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test1 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test1 billing address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test1 billing address "))
            .city("NewYork")
            .state("NY")
            .postalCode("10001")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_CONTACT_ADDR_EDIT_ACCOUNT_TEST2 = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test1 AQA Contact First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test1 AQA Contact Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test1 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test1 contact address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test1 contact address "))
            .city("Austin")
            .state("TX")
            .postalCode("73301")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_BILLING_ADDR_EDIT_ACCOUNT_TEST2 = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test1 AQA Billing First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test1 AQA Billing Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test1 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test1 billing address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test1 billing address "))
            .city("NewYork")
            .state("NY")
            .postalCode("10001")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_CONTACT_ACTIVATE_DEACTIVATE_TEST = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test3 AQA Contact First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test3 AQA Contact Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test3 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test3 contact address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test3 contact address "))
            .city("Austin")
            .state("TX")
            .postalCode("73301")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    public static final Address AQA_BILLING_ACTIVATE_DEACTIVATE_TEST = Address.builder()
            .firstName(generateUniqueStringBasedOnDate("Test3 AQA Billing First Name "))
            .lastName(generateUniqueStringBasedOnDate("Test3 AQA Billing Last Name "))
            .contactTitle(generateUniqueStringBasedOnDate("Test3 AQA "))
            .phoneNumber(generateUniqueFakePhoneNumber())
            .addressOne(generateUniqueStringBasedOnDate("First automation test3 billing address "))
            .addressTwo(generateUniqueStringBasedOnDate("Second automation test3 billing address "))
            .city("NewYork")
            .state("NY")
            .postalCode("10001")
            .country("USA")
            .email(NOTIFICATION_EMAIL)
            .build();

    private PredAddresses() {
        //NONE
    }
}
