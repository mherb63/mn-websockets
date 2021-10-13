package com.mherb.mnaut.service;

import io.micronaut.websocket.CloseReason;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

@Slf4j
@ServerWebSocket("/ws/simple/prices")
public class SimpleWebSocketServer {

    @OnOpen
    public Publisher<String> onOpen(WebSocketSession session) {
        log.debug("Session connected!");
        return session.send("Connected!");
    }

    @OnMessage
    public Publisher<String> onMessage(String message, WebSocketSession session) {
        log.debug("Server received msg: {} from session {}", message, session.getId());

        if (message.equalsIgnoreCase("disconnect me")) {
            log.debug("disconnecting session {}", session.getId());
            session.close(CloseReason.NORMAL);
            return Flowable.empty();
        }
        return session.send("Not supported => (" + message + ")");
    }

    @OnClose
    public void onClose(WebSocketSession session) {
        log.debug("Session closed {}", session.getId());
    }
}
