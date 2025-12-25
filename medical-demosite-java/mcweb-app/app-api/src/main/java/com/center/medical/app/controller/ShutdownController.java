package com.center.medical.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 路飞
 * @date: 2023-05-12 19:16
 * @description:
 */
@RestController
public class ShutdownController {

    private final ShutdownEndpoint shutdownEndpoint;

    @Autowired
    public ShutdownController(ShutdownEndpoint shutdownEndpoint) {
        this.shutdownEndpoint = shutdownEndpoint;
    }

    @PostMapping("/shutdown")
    public void shutdownContext() {
        shutdownEndpoint.shutdown();
    }
}