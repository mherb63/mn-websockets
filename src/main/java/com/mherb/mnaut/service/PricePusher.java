package com.mherb.mnaut.service;

import com.mherb.mnaut.domain.PriceUpdate;
import io.micronaut.scheduling.annotation.Scheduled;
import io.micronaut.websocket.WebSocketBroadcaster;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class PricePusher {

    private final WebSocketBroadcaster webSocketBroadcaster;

    public PricePusher(WebSocketBroadcaster webSocketBroadcaster) {
        this.webSocketBroadcaster = webSocketBroadcaster;
    }

    @Scheduled(fixedDelay = "1s")
    public void push() {
        webSocketBroadcaster.broadcastSync(new PriceUpdate("AMZN", randomValue()));
    }

    private BigDecimal randomValue() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(10, 100));
    }
}
