package com.snobot.simulator.motor_sim;

import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class GravityLoadDcMotorSimTest extends BaseSimulatorJavaTest
{
    @Test
    public void testMotor()
    {
        int motors = 1;
        double efficiency = 1;
        double load = .01;

        SpeedController sc = new Talon(0);
        IPwmWrapper wrapper = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(0);
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", motors, 1.0, efficiency);
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Gravitational(0, motorConfig,
                new GravityLoadMotorSimulationConfig(load)));

        simulateForTime(5, () ->
        {
            sc.set(1);
        });
        Assertions.assertEquals(1, wrapper.getVoltagePercentage(), DOUBLE_EPSILON);

        wrapper.reset();
        Assertions.assertEquals(0, wrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getVelocity(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getCurrent(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getAcceleration(), DOUBLE_EPSILON);

        wrapper.reset(4, 6, 10);
        Assertions.assertEquals(4, wrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(6, wrapper.getVelocity(), DOUBLE_EPSILON);
        Assertions.assertEquals(10, wrapper.getCurrent(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getAcceleration(), DOUBLE_EPSILON);

        Assertions.assertEquals(MotorSimType.GravitationalLoad, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimType(0));
        GravityLoadMotorSimulationConfig simConfig = DataAccessorFactory.getInstance().getSpeedControllerAccessor()
                .getMotorSimGravitationalModelConfig(0);
        Assertions.assertEquals(.01, simConfig.getLoad(), DOUBLE_EPSILON);
    }

    @Test
    public void testInvalidMotor()
    {
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM", 0, 1.0, 0);
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Gravitational(0, motorConfig,
                new GravityLoadMotorSimulationConfig(0)));
    }

}
