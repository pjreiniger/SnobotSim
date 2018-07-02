package com.snobot.simulator;

import java.io.File;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

public final class LogConfigurator
{
    private LogConfigurator()
    {

    }

    public static void loadLog4jConfig()
    {
        File logFile = new File("log4j2.xml");
        URI fileUri = null;
        if (logFile.exists())
        {
            fileUri = logFile.toURI();
        }

        if (fileUri != null)
        {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            if (context != null)
            {
                context.setConfigLocation(fileUri);
            }
        }
    }
}
