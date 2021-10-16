package com.tng.backend.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("events")
@Log4j2
public class ApiHandler {

    @GetMapping("/hello")
    @CrossOrigin(origins = "*")
    public SseEmitter serverSent() {
        SseEmitter sseEmitter = new SseEmitter(86400000L);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        log.info("Request coming");
        executorService.execute(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    sseEmitter.send(SseEmitter.event()
                            .name("hello")
                            .data("message: " + i)
                            .reconnectTime(10000));
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                sseEmitter.completeWithError(e);
            } finally {
                sseEmitter.complete();
            }
        });
        return sseEmitter;
    }
}
