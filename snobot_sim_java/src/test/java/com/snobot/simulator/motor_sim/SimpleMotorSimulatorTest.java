package com.snobot.simulator.motor_sim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class SimpleMotorSimulatorTest extends BaseSimulatorJavaTest
{
    @Test
    public void testSimpleSimulator()
    {
        SpeedController sc = new Talon(0);
        Assertions.assertTrue(
                DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(7.8)));

        simulateForTime(5, () ->
        {
            sc.set(.5);
        });
        Assertions.assertEquals(.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(0), DOUBLE_EPSILON);
        Assertions.assertEquals(19.5, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);

        simulateForTime(3, () ->
        {
            sc.set(-1);
        });
        Assertions.assertEquals(-1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(0), DOUBLE_EPSILON);
        Assertions.assertEquals(-3.9, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);

        Assertions.assertEquals(-3.9, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assertions.assertEquals(-7.8, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getAcceleration(0), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().reset(0);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getAcceleration(0), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().reset(0, 4, 6, 10);
        Assertions.assertEquals(4, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assertions.assertEquals(6, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getAcceleration(0), DOUBLE_EPSILON);

        Assertions.assertEquals(MotorSimType.Simple, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimType(0));
        SimpleMotorSimulationConfig simConfig = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimSimpleModelConfig(0);
        Assertions.assertEquals(7.8, simConfig.mMaxSpeed, DOUBLE_EPSILON);
    }

    @Test
    public void testBadSpeedController()
    {
        Assertions.assertFalse(
                DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(7.8)));

        new Talon(0);
        Assertions.assertTrue(
                DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(10)));

        DcMotorModelConfig createdConfig = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorConfig(0);
        Assertions.assertNull(createdConfig);
    }
}
