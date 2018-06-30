package com.snobot.simulator.module_wrapper;

import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class TestPdpCallback extends BaseSimulatorJniTest
{
    @Test
    public void testPdp()
    {
        new PowerDistributionPanel();
    }
}
