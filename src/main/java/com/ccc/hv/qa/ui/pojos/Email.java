package com.ccc.hv.qa.ui.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import javax.mail.Address;
import java.util.List;

/**
 * Created by R0manL on 18/08/20.
 * DTO has used for reading emails.
 */

@Getter
@Builder
public class Email {
    @NonNull
    @Singular private final List<Address> senders;
    @NonNull
    @Singular private final List<Address> recipients;
    @NonNull
    private final String subject;
    @NonNull
    private final String content;
}
