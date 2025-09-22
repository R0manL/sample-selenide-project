package com.ccc.hv.qa.core;

import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by R0manL.
 */

public class CustomCollectors {

    private CustomCollectors() {
        // NOP
    }

    /**
     * Collector that check if single item exists in given collection. If not throw an exception.
     * @param <T> collection's type
     * @return single element
     */
    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException("Expect single element but found '" + list.size() + "'.");
                    }
                    return list.get(0);
                }
        );
    }
}
