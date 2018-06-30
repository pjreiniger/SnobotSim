package com.snobot.simulator.simulator_components.factory;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.ADXL345_SPI;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

public class TestSpiFactory extends BaseSimulatorJniTest
{
    @Test
    public void testAvailableDataTypes()
    {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Collection<String> available = DataAccessorFactory.getInstance().getSimulatorDataAccessor().getAvailableSpiSimulators();

        Assertions.assertEquals(4, available.size());
        Assertions.assertTrue(available.contains("NavX"));
        Assertions.assertTrue(available.contains("ADXRS450"));
        Assertions.assertTrue(available.contains("ADXL345"));
        Assertions.assertTrue(available.contains("ADXL362"));
    }

    @Test
    public void testInvalidType()
    {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().createSpiSimulator(0, "DoesntExist");

        new ADXL345_SPI(SPI.Port.kOnboardCS0, Range.k2G);
    }
}
