package com.snobot.simulator.motor_factory;

import org.junit.Assert;
import org.junit.Test;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.motor_factory.VexMotorFactory;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorTest;

import edu.wpi.first.wpilibj.Talon;

public class MotorFactoryTest extends BaseSimulatorTest
{
    @Test
    public void testInvalidMotorName()
    {
        DcMotorModelConfig config = VexMotorFactory.createMotor("DoesNotExist");

        Assert.assertEquals(0, config.mMotorParams.mFreeCurrent, DOUBLE_EPSILON);
        Assert.assertEquals(0, config.mMotorParams.mFreeSpeedRpm, DOUBLE_EPSILON);
        Assert.assertEquals(0, config.mMotorParams.mFreeCurrent, DOUBLE_EPSILON);
        Assert.assertEquals(0, config.mMotorParams.mStallTorque, DOUBLE_EPSILON);
        Assert.assertEquals(0, config.mMotorParams.mStallCurrent, DOUBLE_EPSILON);
        Assert.assertEquals(0, config.mMotorParams.mFreeCurrent, DOUBLE_EPSILON);
        Assert.assertEquals(0, config.mMotorParams.mMortorInertia, DOUBLE_EPSILON);
    }

    @Test
    public void testCreateCIM()
    {
        new Talon(0);
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM");
        Assert.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(0, motorConfig,
                new StaticLoadMotorSimulationConfig(12)));

        DcMotorModelConfig createdConfig = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorConfig(0);

        Assert.assertEquals(motorConfig, createdConfig);
    }
}
