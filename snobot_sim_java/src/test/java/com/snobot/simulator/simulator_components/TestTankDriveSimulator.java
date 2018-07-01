package com.snobot.simulator.simulator_components;

import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.simulator_components.config.TankDriveConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class TestTankDriveSimulator extends BaseSimulatorJavaTest
{
    @Test
    public void testTankDrive()
    {
        final SpeedController rightSC = new Talon(0);
        final SpeedController leftSC = new Talon(1);
        final Encoder rightEnc = new Encoder(0, 1);
        final Encoder leftEnc = new Encoder(2, 3);
        final Gyro gyro = new AnalogGyro(0);

        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(0, 0));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getEncoderAccessor().connectSpeedController(1, 1));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(1)));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(1, new SimpleMotorSimulationConfig(1)));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(1, 0, 0, 180 / Math.PI));

        // Turn Left
        simulateForTime(90, () ->
        {
            rightSC.set(1);
            leftSC.set(-1);
        });
        Assertions.assertEquals(-180, gyro.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(89, rightEnc.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(-89, leftEnc.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(-180, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(0), DOUBLE_EPSILON);
        Assertions.assertEquals(90, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);
        Assertions.assertEquals(-90, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(1), DOUBLE_EPSILON);

        // Turn right
        simulateForTime(45, () ->
        {
            rightSC.set(-1);
            leftSC.set(1);
        });
        Assertions.assertEquals(-90, gyro.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(45, rightEnc.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(-45, leftEnc.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(-90, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(0), DOUBLE_EPSILON);
        Assertions.assertEquals(45, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(0), DOUBLE_EPSILON);
        Assertions.assertEquals(-45, DataAccessorFactory.getInstance().getEncoderAccessor().getDistance(1), DOUBLE_EPSILON);
    }

    @Test
    public void testInvalidSetup()
    {
        // Everything null
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(0, 1, 0, 180 / Math.PI);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().updateSimulatorComponents(0); // shouldn't do anything

        // First thing not null
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        new Encoder(0, 1);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(0, 1, 0, 180 / Math.PI);

        // First two things not null
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        new Encoder(0, 1);
        new Encoder(2, 3);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(0, 1, 0, 180 / Math.PI);

        // Third thing not null
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        new AnalogGyro(0);
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(0, 1, 0, 180 / Math.PI);
    }

    @Test
    public void testSimulatorComponentRegister()
    {
        new Encoder(0, 1);
        new Encoder(2, 3);
        new AnalogGyro(0);

        DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(0, 1, 0, 180 / Math.PI);

        Collection<Object> configs = DataAccessorFactory.getInstance().getSimulatorDataAccessor().getSimulatorComponentConfigs();
        Assertions.assertEquals(1, configs.size());

        TankDriveConfig config = (TankDriveConfig) configs.iterator().next();
        Assertions.assertEquals(0, config.getmGyroHandle());
        Assertions.assertEquals(0, config.getmLeftEncoderHandle());
        Assertions.assertEquals(1, config.getmRightEncoderHandle());
        Assertions.assertEquals(180 / Math.PI, config.getmTurnKp(), DOUBLE_EPSILON);
    }
}
