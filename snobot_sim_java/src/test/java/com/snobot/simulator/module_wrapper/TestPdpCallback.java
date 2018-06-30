package com.snobot.simulator.module_wrapper;

import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class TestPdpCallback extends BaseSimulatorJavaTest
{
    @Test
    public void testPdp()
    {
        new PowerDistributionPanel();
    }
}
