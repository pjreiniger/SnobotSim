package com.snobot.simulator.motor_sim;

import org.junit.Assert;
import org.junit.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;
import com.snobot.test.utilities.BaseSimulatorTest;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class GravityLoadDcMotorSimTest extends BaseSimulatorTest
{
    @Test
    public void testMotor()
    {
        int motors = 1;
        double efficiency = 1;
        double load = .01;

        SpeedController sc = new Talon(0);
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", motors, 1.0, efficiency);
        Assert.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Gravitational(0, motorConfig,
                new GravityLoadMotorSimulationConfig(load)));

        simulateForTime(5, () ->
        {
            sc.set(1);
        });
        Assert.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(0), DOUBLE_EPSILON);

        Assert.assertNotEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assert.assertNotEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assert.assertNotEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);
        Assert.assertNotEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getAcceleration(0), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().reset(0);
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getAcceleration(0), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().reset(0, 4, 6, 10);
        Assert.assertEquals(4, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assert.assertEquals(6, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assert.assertEquals(10, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);
        Assert.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getAcceleration(0), DOUBLE_EPSILON);

        Assert.assertEquals(MotorSimType.GravitationalLoad, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimType(0));
        GravityLoadMotorSimulationConfig simConfig = DataAccessorFactory.getInstance().getSpeedControllerAccessor()
                .getMotorSimGravitationalModelConfig(0);
        Assert.assertEquals(.01, simConfig.getLoad(), DOUBLE_EPSILON);
    }

    @Test
    public void testInvalidMotor()
    {
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 0, 1.0, 0);
        Assert.assertFalse(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Gravitational(0, motorConfig,
                new GravityLoadMotorSimulationConfig(0)));
    }

}
