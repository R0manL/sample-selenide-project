package com.ccc.hv.qa.api.pojo;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Created by R0manL on 22/02/21.
 */

@Getter
@SuperBuilder
public class BlackoutsStep {
    private final List<BlackoutDateAPI> dates;
}
