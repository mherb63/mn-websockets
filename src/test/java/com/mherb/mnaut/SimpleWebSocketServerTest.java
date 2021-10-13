package com.mherb.mnaut;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava2.http.client.websockets.RxWebSocketClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
@Slf4j
public class SimpleWebSocketServerTest {

    @Inject
    @Client("http://localhost:8180")
    RxWebSocketClient client;

    SimpleWebSocketClient webSocketClient;

    @BeforeEach
    void connect() {
        webSocketClient = client.connect(SimpleWebSocketClient.class, "/ws/simple/prices").blockingFirst();
        log.debug("Client Session: {}", webSocketClient.getSession());
    }

    @Test
    void testCanReceiveMessagesWithClient() {
        webSocketClient.send("Hello There!!!");

        Awaitility.await().timeout(Duration.ofSeconds(5)).untilAsserted(() -> {
            final Object[] messages = webSocketClient.getObservedMessages().toArray();
            log.debug("Observed Messages size: {}, messages: {}", webSocketClient.getObservedMessages().size(), messages);

            assertEquals("Connected!", messages[0]);
            assertEquals("Not supported => (Hello There!!!)", messages[1]);
        });
    }

    @Test
    void canSendReactively() {
        log.debug("Sent {}", webSocketClient.sendReactive("Hello There Reactive!!!").blockingGet());

        Awaitility.await().timeout(Duration.ofSeconds(5)).untilAsserted(() -> {
            final Object[] messages = webSocketClient.getObservedMessages().toArray();
            log.debug("Observed Messages size: {}, messages: {}", webSocketClient.getObservedMessages().size(), messages);

            assertEquals("Connected!", messages[0]);
            assertEquals("Not supported => (Hello There Reactive!!!)", messages[1]);
        });
    }
}
