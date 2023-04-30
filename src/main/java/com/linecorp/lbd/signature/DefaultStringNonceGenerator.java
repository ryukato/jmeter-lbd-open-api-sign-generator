package com.linecorp.lbd.signature;

import java.security.SecureRandom;

import org.apache.commons.lang3.RandomStringUtils;

public class DefaultStringNonceGenerator {

    public String newNonce() {
        return RandomStringUtils.random(
                8, 0, 0, true, true, null, new SecureRandom()
        );
    }

    public static DefaultStringNonceGenerator createInstance() {
        return new DefaultStringNonceGenerator();
    }
}
