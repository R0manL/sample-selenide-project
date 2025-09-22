package com.ccc.hv.qa.api.pojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

/**
 * Created by R0manL on 22/02/21.
 */

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SaveError {
    String field;
    String message;
}
