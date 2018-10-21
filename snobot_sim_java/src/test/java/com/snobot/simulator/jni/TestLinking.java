package com.snobot.simulator.jni;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.hal.HAL;

public class TestLinking extends BaseSimulatorJavaTest
{
    @Test
    public void testHalLinking()
    {
        Assertions.assertTrue(HAL.initialize(0, 0));
    }

    @Test
    public void testSnobotSimLinking()
    {
        RegisterCallbacksJni.reset();
    }
}
