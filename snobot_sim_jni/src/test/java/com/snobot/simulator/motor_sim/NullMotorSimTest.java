package com.snobot.simulator.motor_sim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class NullMotorSimTest extends BaseSimulatorJniTest
{
    @Test
    public void testMotor()
    {
        SpeedController sc = new Talon(0);
        simulateForTime(5, () ->
        {
            sc.set(1);
        });

        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getAcceleration(0), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().reset(0);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getAcceleration(0), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().reset(0, 5, 5, 5);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);

        Assertions.assertEquals(MotorSimType.None, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimType(0));
    }

    @Test
    public void testInvalidSetup()
    {
        final SpeedController sc = new Talon(0);
        DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimSimpleModelConfig(0);
        DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimStaticModelConfig(0);
        DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimGravitationalModelConfig(0);
        DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimRotationalModelConfig(0);

        Assertions.assertNotNull(sc); // findbugs supression
    }
}
