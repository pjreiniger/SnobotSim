package com.snobot.simulator.simulator_components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.BaseSimulatorJavaTest;


public class TestCompressorWrapper extends BaseSimulatorJavaTest
{
    @Test
    public void testCompressorWrapper()
    {
        CompressorWrapper compressor = new CompressorWrapper();
        compressor.setChargeRate(.1);

        Assertions.assertEquals(120, compressor.getAirPressure(), DOUBLE_EPSILON);

        compressor.solenoidFired(60);
        Assertions.assertEquals(60, compressor.getAirPressure(), DOUBLE_EPSILON);

        simulateForTime(2, () ->
        {
            compressor.update();
        });
        Assertions.assertEquals(70, compressor.getAirPressure(), DOUBLE_EPSILON);

        // Get it down past 0
        compressor.solenoidFired(90);
        Assertions.assertEquals(0, compressor.getAirPressure(), DOUBLE_EPSILON);

        // Let it charge back up
        simulateForTime(30, () ->
        {
            compressor.update();
        });
        Assertions.assertEquals(120, compressor.getAirPressure(), DOUBLE_EPSILON);

    }
}
