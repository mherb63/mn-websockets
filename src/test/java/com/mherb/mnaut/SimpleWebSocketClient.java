package com.mherb.mnaut;

import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.ClientWebSocket;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.reactivex.Single;
import lombok.Data;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

@ClientWebSocket("/ws/simple/prices")
@Data
public abstract class SimpleWebSocketClient implements AutoCloseable {

    private WebSocketSession session;
    private final Collection<String> observedMessages = new ConcurrentLinkedQueue<>();

    @OnOpen
    public void onOpen(WebSocketSession session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        observedMessages.add(message);
    }

    public abstract void send(String message);

    public abstract Single<String> sendReactive(String message);
}
