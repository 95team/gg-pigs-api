package com.gg_pigs._common.utility.redis;

public interface RedisClient {

    String getStringValue(String key);

    boolean storeStringValue(String key, String value);

    boolean storeStringValueForSeconds(String key, String value, int timeout);
}
