package com.linecorp.lbd.signature;

import java.time.Clock;

public class DefaultGlobalTimestampProvider {
   private static final Clock globalClock = Clock.systemUTC();
    public Long timestamp() {
        return globalClock.millis();
    }
    public static DefaultGlobalTimestampProvider createInstance() {
        return new DefaultGlobalTimestampProvider();
    }
}
