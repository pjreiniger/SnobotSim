package com.snobot.simulator.module_wrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.AnalogGyro;

public class TestAnalogGyroJni extends BaseSimulatorJniTest
{
    @Test
    public void testAnalogGyro()
    {
        AnalogGyro gyro = new AnalogGyro(0);

        int gyroHandle = 0;
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(gyroHandle));

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(gyroHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(0, gyro.getAngle(), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(gyroHandle, 90);
        Assertions.assertEquals(90, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(gyroHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(90, gyro.getAngle(), DOUBLE_EPSILON);
    }

    @Test
    public void testAnalogGyroPreSetup()
    {
        int gyroHandle = 1;

        DataAccessorFactory.getInstance().getGyroAccessor().createSimulator(gyroHandle, "WpiAnalogGyroWrapper");
        Assertions.assertFalse(DataAccessorFactory.getInstance().getGyroAccessor().isInitialized(gyroHandle));

        new AnalogGyro(gyroHandle);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().isInitialized(gyroHandle));

    }

}
