package com.ocpp201.websocket.websockets201.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.io.File;

@Slf4j
public class OpenLogOnStart implements ApplicationListener<ApplicationReadyEvent> {

    private static final String LOG_FILE = "logs/server.log";

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            File logFile = new File(LOG_FILE);
            if (!logFile.exists()) {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            }

            // Windows command to open Notepad
            Runtime.getRuntime().exec("notepad.exe " + LOG_FILE);
            log.info("Log file opened in Notepad at startup.");
        } catch (Exception e) {
            log.error("Failed to open log file in Notepad", e);
        }
    }
}
