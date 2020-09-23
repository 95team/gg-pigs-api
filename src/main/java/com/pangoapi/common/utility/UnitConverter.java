package com.pangoapi.common.utility;

import java.util.Arrays;
import java.util.List;

public class UnitConverter {

    private static final List<String> SUPPORT_UNIT_LIST = Arrays.asList("KiB", "MiB", "GiB", "TiB");
    private static final List<Long> SUPPORT_UNIT_VALUE_LIST = Arrays.asList(1000L, 1000000L, 1000000000L, 1000000000L);

    public static final String convertToUnit(long targetValue, String targetUnit) {
        String convertResult = "-";

        if(!SUPPORT_UNIT_LIST.contains(targetUnit)) return convertResult;

        long convertValue = SUPPORT_UNIT_VALUE_LIST.get(SUPPORT_UNIT_LIST.indexOf(targetUnit));
        convertResult = String.format("%.2f", (double) targetValue / convertValue) + targetUnit;

        return convertResult;
    }
}
