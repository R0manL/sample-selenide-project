package com.ccc.hv.qa.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

/**
 * Created by R0manL on 22/02/21.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SaveResponse {
    boolean passed;
    String successResponse;
    String generalError;
    String generalErrorObject;
    List<SaveError> errors;
}
