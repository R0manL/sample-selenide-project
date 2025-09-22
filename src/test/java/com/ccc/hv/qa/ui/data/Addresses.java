package com.ccc.hv.qa.ui.data;

import com.ccc.hv.qa.ui.pojos.Address;

import static com.ccc.hv.qa.utils.EmailUtils.generateUniqueFakeEmailAddress;
import static com.ccc.hv.qa.utils.StringUtils.*;

/**
 * Created by R0manL on 20/08/20.
 */

public class Addresses {
    private static final String UNIQUE_POSTFIX = generateUniqueStringBasedOnDate();

    private Addresses() {
        //NONE
    }

    /** Smoke test set addresses */
    public static final Address smokeUserContactAddress = Address.builder()
            .firstName("SmokeContactFirstName")
            .lastName("SmokeContactLastName")
            .phoneNumber("+16469801111")
            .addressOne("95111 Melodee Spring")
            .addressTwo("23489 Teddi Ave")
            .city("NewYork")
            .state("NY")
            .postalCode("3333-22")
            .country("USA")
            .email(generateUniqueFakeEmailAddress(""))
            .contactTitle("ContactTitle")
            .build();

    public static final Address smokeBillingAddress = Address.builder()
            .firstName("Smoke_BillingFirstName")
            .lastName("Smoke_BillingLastName")
            .phoneNumber("+16469804219")
            .addressOne("95140 Melodee Spring")
            .addressTwo("89045 Long Ave")
            .city("NewYork")
            .state("NY")
            .postalCode("8888-44")
            .country("USA")
            .email(generateUniqueFakeEmailAddress(""))
            .contactTitle("BillingTitle")
            .build();

    public static final Address smokeChannelContactInfo = Address.builder()
            .firstName("Smoke contact FN " + UNIQUE_POSTFIX)
            .lastName("Smoke Contact LN " + UNIQUE_POSTFIX)
            .phoneNumber(generateUniqueFakePhoneNumber())
            .email(generateUniqueFakeEmailAddress(UNIQUE_POSTFIX))
            .contactTitle("Smoke contact title " + UNIQUE_POSTFIX)
            .addressOne("Smoke address" + UNIQUE_POSTFIX)
            .city("Smoke test city" + UNIQUE_POSTFIX)
            .state("SMOKE AQA")
            .postalCode("345678")
            .country("US")
            .build();

    public static final Address EMPTY_BILLING_INFO = Address.builder()
            .firstName("")
            .lastName("")
            .contactTitle("")
            .phoneNumber("")
            .addressOne("")
            .addressTwo("")
            .city("")
            .state("")
            .postalCode("")
            .country("")
            .email("")
            .build();
    /* end of Smoke test set addresses */

    /* Regression test set addresses */
    public static final Address simpleContactInfo = Address.builder()
            .firstName("Regression simple contact FN " + UNIQUE_POSTFIX)
            .lastName("Regression simple Contact LN " + UNIQUE_POSTFIX)
            .phoneNumber(generateUniqueFakePhoneNumber())
            .email(generateUniqueFakeEmailAddress(UNIQUE_POSTFIX))
            .contactTitle("Regression simple contact title " + UNIQUE_POSTFIX)
            .addressOne("Regression simple address" + UNIQUE_POSTFIX)
            .city("Regression simple test city" + UNIQUE_POSTFIX)
            .state("REG AQA")
            .postalCode("345679")
            .country("US")
            .build();

    public static final Address channelAssociationContactInfo = Address.builder()
            .firstName("Smoke contact FN " + UNIQUE_POSTFIX)
            .lastName("Smoke Contact LN " + UNIQUE_POSTFIX)
            .phoneNumber(generateUniqueFakePhoneNumber())
            .email(generateUniqueFakeEmailAddress(UNIQUE_POSTFIX))
            .contactTitle("Smoke contact title " + UNIQUE_POSTFIX)
            .addressOne("Smoke address" + UNIQUE_POSTFIX)
            .addressTwo("Smoke address 2" + UNIQUE_POSTFIX)
            .city("Smoke test city" + UNIQUE_POSTFIX)
            .state("SMOKE AQA")
            .postalCode("345678")
            .country("US")
            .build();

    /* end of Regression test set addresses */
}
