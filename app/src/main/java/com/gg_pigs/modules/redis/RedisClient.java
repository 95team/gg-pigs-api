package com.gg_pigs.modules.redis;

public interface RedisClient {

    String getStringValue(String key);

    boolean storeStringValue(String key, String value);

    boolean storeStringValueForSeconds(String key, String value, int timeout);
}
