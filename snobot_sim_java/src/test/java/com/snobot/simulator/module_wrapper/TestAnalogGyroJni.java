package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.module_wrapper.wpi.WpiAnalogGyroWrapper;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.AnalogGyro;

public class TestAnalogGyroJni extends BaseSimulatorJavaTest
{
    @Test
    public void testAnalogGyro()
    {
        AnalogGyro gyro = new AnalogGyro(0);

        int gyroHandle = 0;
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(gyroHandle));
        IGyroWrapper gyroWrapper = DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(gyroHandle);

        Assertions.assertEquals(0, gyroWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, gyro.getAngle(), DOUBLE_EPSILON);


        gyroWrapper.setAngle(90);
        Assertions.assertEquals(90, gyroWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(90, gyro.getAngle(), DOUBLE_EPSILON);
    }

    @Test
    public void testAnalogGyroPreSetup()
    {
        int gyroHandle = 1;

        DataAccessorFactory.getInstance().getGyroAccessor().createSimulator(gyroHandle, WpiAnalogGyroWrapper.class.getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(gyroHandle).isInitialized());

        new AnalogGyro(gyroHandle);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(gyroHandle).isInitialized());

    }

}
