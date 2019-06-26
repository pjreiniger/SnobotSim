package com.snobot.simulator.module_wrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.module_wrapper.wpi.WpiPwmWrapper;
import com.snobot.simulator.simulator_components.IMotorFeedbackSensor;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Talon;

public class TestPwmJni extends BaseSimulatorJavaTest
{
    @Test
    public void testCreatePwm()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        new Jaguar(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        Assertions.assertEquals("Speed Controller 0", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(0));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWantsHidden(0));

        new Talon(3);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());
        Assertions.assertEquals("Speed Controller 3", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(3));
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWantsHidden(3));

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().setName(0, "NewNameFor0");
        Assertions.assertEquals("NewNameFor0", DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(0));
    }

    @Test
    public void testCreatePwmWithSetup()
    {
        DataAccessorFactory.getInstance().getSpeedControllerAccessor().createSimulator(3, WpiPwmWrapper.class.getName());
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSpeedControllerAccessor().isInitialized(3));

        new Talon(3);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSpeedControllerAccessor().isInitialized(3));
    }

    @Test
    public void testReusePort()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        new Talon(0);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        Assertions.assertThrows(RuntimeException.class, () ->
        {
            new Talon(0);
        });
    }

    @Test
    public void testSet()
    {
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList().size());

        Talon talon = new Talon(1);
        Assertions.assertEquals(0, talon.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(1), DOUBLE_EPSILON);

        talon.set(.5);
        Assertions.assertEquals(.5, talon.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(1), DOUBLE_EPSILON);

        talon.set(-.5);
        Assertions.assertEquals(-.5, talon.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(-.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(1), DOUBLE_EPSILON);

        talon.set(1.1);
        Assertions.assertEquals(1.0, talon.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(1.0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(1), DOUBLE_EPSILON);

        talon.set(-2.1);
        Assertions.assertEquals(-1.0, talon.get(), DOUBLE_EPSILON);
        Assertions.assertEquals(-1.0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(1), DOUBLE_EPSILON);
    }

    @Test
    public void testNullFeedbackDevice()
    {
        IMotorFeedbackSensor feedbackSensor = new IMotorFeedbackSensor.NullFeedbackSensor();

        feedbackSensor.setPosition(10);
        Assertions.assertEquals(0, feedbackSensor.getPosition(), DOUBLE_EPSILON);
    }
}
