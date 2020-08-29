package com.snobot.simulator.motor_sim;

import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class SimpleMotorSimulatorTest extends BaseSimulatorJniTest
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

        IPwmWrapper wrapper = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(0);
        Assertions.assertEquals(.5, wrapper.getVoltagePercentage(), DOUBLE_EPSILON);
        Assertions.assertEquals(19.5, wrapper.getPosition(), DOUBLE_EPSILON);

        simulateForTime(3, () ->
        {
            sc.set(-1);
        });
        Assertions.assertEquals(-1, wrapper.getVoltagePercentage(), DOUBLE_EPSILON);
        Assertions.assertEquals(-3.9, wrapper.getPosition(), DOUBLE_EPSILON);

        Assertions.assertEquals(-3.9, wrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(-7.8, wrapper.getVelocity(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getCurrent(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getAcceleration(), DOUBLE_EPSILON);

        wrapper.reset();
        Assertions.assertEquals(0, wrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getVelocity(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getCurrent(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getAcceleration(), DOUBLE_EPSILON);

        wrapper.reset(4, 6, 10);
        Assertions.assertEquals(4, wrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(6, wrapper.getVelocity(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getCurrent(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getAcceleration(), DOUBLE_EPSILON);

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
