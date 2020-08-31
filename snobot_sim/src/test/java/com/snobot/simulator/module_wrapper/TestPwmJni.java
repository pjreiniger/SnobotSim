package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Talon;

public class TestPwmJni extends BaseSimulatorJniTest
{
    @Test
    public void testCreatePwm()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().size());

        new Jaguar(0);
        IPwmWrapper wrapper0 = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().size());
        Assertions.assertEquals("Speed Controller 0", wrapper0.getName());
        Assertions.assertFalse(wrapper0.getWantsHidden());

        new Talon(3);
        IPwmWrapper wrapper3 = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().size());
        Assertions.assertEquals("Speed Controller 3", wrapper3.getName());
        Assertions.assertFalse(wrapper3.getWantsHidden());

        wrapper0.setName("NewNameFor0");
        Assertions.assertEquals("NewNameFor0", wrapper0.getName());
    }

    @Test
    public void testCreatePwmWithSetup()
    {
        IPwmWrapper wrapper = DataAccessorFactory.getInstance().getSpeedControllerAccessor().createSimulator(3, "com.snobot.simulator.module_wrapper.wpi.WpiPwmWrapper");
        Assertions.assertFalse(wrapper.isInitialized());

        new Talon(3);
        Assertions.assertTrue(wrapper.isInitialized());
    }

    @Test
    public void testReusePort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().size());

        new Talon(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new Talon(0);
        });
    }

    @Test
    public void testSet()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrappers().size());

        Talon talon = new Talon(1);
        IPwmWrapper wrapper = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(1);
        Assertions.assertEquals(0, talon.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getVoltagePercentage(), DOUBLE_EPSILON);

        talon.set(.5);
        Assertions.assertEquals(.5, talon.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(.5, wrapper.getVoltagePercentage(), DOUBLE_EPSILON);

        talon.set(-.5);
        Assertions.assertEquals(-.5, talon.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(-.5, wrapper.getVoltagePercentage(), DOUBLE_EPSILON);

        talon.set(1.1);
        Assertions.assertEquals(1.0, talon.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(1.0, wrapper.getVoltagePercentage(), DOUBLE_EPSILON);

        talon.set(-2.1);
        Assertions.assertEquals(-1.0, talon.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(-1.0, wrapper.getVoltagePercentage(), DOUBLE_EPSILON);
    }
}
