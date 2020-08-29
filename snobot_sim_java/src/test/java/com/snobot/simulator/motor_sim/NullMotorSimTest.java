package com.snobot.simulator.motor_sim;

import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class NullMotorSimTest extends BaseSimulatorJavaTest
{
    @Test
    public void testMotor()
    {
        SpeedController sc = new Talon(0);
        simulateForTime(5, () ->
        {
            sc.set(1);
        });

        IPwmWrapper wrapper = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(0);

        Assertions.assertEquals(0, wrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getVelocity(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getCurrent(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getAcceleration(), DOUBLE_EPSILON);

        wrapper.reset();
        Assertions.assertEquals(0, wrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getVelocity(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getCurrent(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getAcceleration(), DOUBLE_EPSILON);

        wrapper.reset(5, 5, 5);
        Assertions.assertEquals(0, wrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getVelocity(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getCurrent(), DOUBLE_EPSILON);

        Assertions.assertEquals(MotorSimType.None, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimType(0));
    }

    @Test
    public void testInvalidSetup()
    {
        SpeedController sc = new Talon(0);
        Assertions.assertNotNull(sc); // suppress findbugs
        DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimSimpleModelConfig(0);
        DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimStaticModelConfig(0);
        DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimGravitationalModelConfig(0);
        DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimRotationalModelConfig(0);
    }
}
