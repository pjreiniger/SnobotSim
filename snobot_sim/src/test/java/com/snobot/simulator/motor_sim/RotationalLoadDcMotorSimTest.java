package com.snobot.simulator.motor_sim;

import java.io.IOException;

import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class RotationalLoadDcMotorSimTest extends BaseSimulatorJniTest
{
    @Test
    public void testMotor() throws IOException
    {
        double dt = .0001;

        double armCenterOfMass = .82;  // m
        double armMass = .2;  // kg

        SpeedController sc = new Talon(0);
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM");
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Rotational(0, motorConfig,
                new RotationalLoadMotorSimulationConfig(armCenterOfMass, armMass)));

        simulateForTime(5, () ->
        {
            sc.set(1);
        });

        IPwmWrapper wrapper = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getWrapper(0);

        Assertions.assertEquals(1, wrapper.getVoltagePercentage(), DOUBLE_EPSILON);

        wrapper.reset();
        Assertions.assertEquals(0, wrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getVelocity(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getCurrent(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getAcceleration(), DOUBLE_EPSILON);

        wrapper.reset(1, 2, 3);
        Assertions.assertEquals(1, wrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(2, wrapper.getVelocity(), DOUBLE_EPSILON);
        Assertions.assertEquals(3, wrapper.getCurrent(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, wrapper.getAcceleration(), DOUBLE_EPSILON);

        Assertions.assertEquals(MotorSimType.RotationalLoad, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimType(0));
        RotationalLoadMotorSimulationConfig simConfig = DataAccessorFactory.getInstance().getSpeedControllerAccessor()
                .getMotorSimRotationalModelConfig(0);
        Assertions.assertEquals(.82, simConfig.mArmCenterOfMass, DOUBLE_EPSILON);
        Assertions.assertEquals(.2, simConfig.mArmMass, DOUBLE_EPSILON);
    }

    @Test
    public void testInvalidMotor()
    {
        DcMotorModelConfig motorConfig = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor("CIM");
        Assertions.assertFalse(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Rotational(0, motorConfig,
                new RotationalLoadMotorSimulationConfig(0, 0)));
    }

}
