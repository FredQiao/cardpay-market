package com.arraypay.market.util;

import java.util.Random;

public class StringUtils {

    public static String getRandom() {
        return String.valueOf(new Random().nextInt(999999)%900000+100000);
    }
}
