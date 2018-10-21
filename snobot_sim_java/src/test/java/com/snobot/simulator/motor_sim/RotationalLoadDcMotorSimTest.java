package com.snobot.simulator.motor_sim;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class RotationalLoadDcMotorSimTest extends BaseSimulatorJavaTest
{
    @Test
    public void testMotor() throws IOException
    {
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
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVoltagePercentage(0), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().reset(0);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getAcceleration(0), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getSpeedControllerAccessor().reset(0, 1, 2, 3);
        Assertions.assertEquals(1, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPosition(0), DOUBLE_EPSILON);
        Assertions.assertEquals(2, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getVelocity(0), DOUBLE_EPSILON);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getCurrent(0), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getSpeedControllerAccessor().getAcceleration(0), DOUBLE_EPSILON);

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
