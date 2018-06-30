package com.snobot.simulator;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPluginSniffer
{
    @Test
    public void testPluginSnifferWithNoPlugins() throws Exception
    {
        PluginSniffer sniffer = new PluginSniffer();
        sniffer.loadPlugins(new File("test_files/plugins"));

        Assertions.assertEquals(0, sniffer.getCppRobots().size());
        Assertions.assertEquals(0, sniffer.getJavaRobots().size());
    }
}
