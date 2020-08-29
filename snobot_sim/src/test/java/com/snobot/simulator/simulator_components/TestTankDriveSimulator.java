package com.snobot.simulator.simulator_components;

import com.snobot.simulator.module_wrapper.interfaces.IEncoderWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import com.snobot.simulator.module_wrapper.interfaces.IPwmWrapper;
import com.snobot.simulator.wrapper_accessors.EncoderWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.GyroWrapperAccessor;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class TestTankDriveSimulator extends BaseSimulatorJniTest
{
    @Test
    public void testTankDrive()
    {
        SpeedControllerWrapperAccessor scAccessor = DataAccessorFactory.getInstance().getSpeedControllerAccessor();
        EncoderWrapperAccessor encoderAccessor = DataAccessorFactory.getInstance().getEncoderAccessor();
        GyroWrapperAccessor gyroAccessor = DataAccessorFactory.getInstance().getGyroAccessor();

        final SpeedController rightSC = new Talon(0);
        final SpeedController leftSC = new Talon(1);
        final Encoder rightEnc = new Encoder(0, 1);
        final Encoder leftEnc = new Encoder(2, 3);
        final Gyro gyro = new AnalogGyro(0);

        IPwmWrapper rightScWrapper = scAccessor.getWrapper(0);
        IPwmWrapper leftScWrapper = scAccessor.getWrapper(1);
        IEncoderWrapper rightEncWrapper = encoderAccessor.getWrapper(0);
        IEncoderWrapper leftEncWrapper = encoderAccessor.getWrapper(1);

        Assertions.assertTrue(rightEncWrapper.connectSpeedController(rightScWrapper));
        Assertions.assertTrue(leftEncWrapper.connectSpeedController(leftScWrapper));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(0, new SimpleMotorSimulationConfig(1)));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(1, new SimpleMotorSimulationConfig(1)));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getSimulatorDataAccessor().connectTankDriveSimulator(1, 0, 0, 180 / Math.PI));

        IGyroWrapper gyroWrapper = gyroAccessor.getWrapper(0);

        // Turn Left
        simulateForTime(90, () ->
        {
            rightSC.set(1);
            leftSC.set(-1);
        });
        Assertions.assertEquals(-180, gyro.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(89, rightEnc.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(-89, leftEnc.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(-180, gyroWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(90, rightEncWrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(-90, leftEncWrapper.getPosition(), DOUBLE_EPSILON);

        // Turn right
        simulateForTime(45, () ->
        {
            rightSC.set(-1);
            leftSC.set(1);
        });
        Assertions.assertEquals(-90, gyro.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(45, rightEnc.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(-45, leftEnc.getDistance(), DOUBLE_EPSILON);
        Assertions.assertEquals(-90, gyroWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(45, rightEncWrapper.getPosition(), DOUBLE_EPSILON);
        Assertions.assertEquals(-45, leftEncWrapper.getPosition(), DOUBLE_EPSILON);
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
}
