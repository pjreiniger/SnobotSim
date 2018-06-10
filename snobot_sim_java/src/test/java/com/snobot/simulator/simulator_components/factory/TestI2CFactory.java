package com.snobot.simulator.simulator_components.factory;

import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

public class TestI2CFactory extends BaseSimulatorJavaTest
{
    @Test
    public void testAvailableDataTypes()
    {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Collection<String> available = DataAccessorFactory.getInstance().getSimulatorDataAccessor().getAvailableI2CSimulators();

        Assertions.assertEquals(2, available.size());
        Assertions.assertTrue(available.contains("NavX"));
        Assertions.assertTrue(available.contains("ADXL345"));
    }

    @Test
    public void testInvalidType()
    {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setDefaultSpiSimulator(0, "DoesntExist");

        new ADXL345_I2C(I2C.Port.kOnboard, Range.k2G);
    }

    @Test
    public void testDefaultI2CWrappers()
    {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setDefaultI2CSimulator(0, "TestA");
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setDefaultI2CSimulator(1, "TestB");
        Map<Integer, String> defaults = DataAccessorFactory.getInstance().getSimulatorDataAccessor().getDefaultI2CWrappers();

        Assertions.assertEquals(2, defaults.size());
        Assertions.assertEquals("TestA", defaults.get(0));
        Assertions.assertEquals("TestB", defaults.get(1));
    }
}
