package com.snobot.simulator.jni;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.jni.module_wrapper.SpeedControllerWrapperJni;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.hal.HAL;

public class TestLinking extends BaseSimulatorJniTest
{
    @Test
    public void testHalLinking()
    {
        Assertions.assertTrue(HAL.initialize(0, 0));
    }

    @Test
    public void testSnobotSimLinking()
    {
        Assertions.assertEquals(0, SpeedControllerWrapperJni.getPortList().length);
    }
}
