package com.snobot.simulator.simulator_components.ctre;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.FusionStatus;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

@Tag("CTRE")
public class TestPigeonImu extends BaseSimulatorJavaTest
{
    @ParameterizedTest
    @ArgumentsSource(GetCtreTestIds.GetCtreTestIdsFeedbackDevice.class)
    public void testPigeonInSeries(int aDeviceId)
    {
        PigeonIMU imu = new PigeonIMU(aDeviceId);
        testImu(imu, aDeviceId);
    }

    @ParameterizedTest
    @ArgumentsSource(GetCtreTestIds.GetCtreTestIdsFeedbackDevice.class)
    public void testPigeonInTalon(int aDeviceId)
    {
        TalonSRX talon = new TalonSRX(aDeviceId);
        PigeonIMU imu = new PigeonIMU(talon);
        testImu(imu, aDeviceId);
    }

    private void testImu(PigeonIMU aImu, int aDeviceId)
    {
        final double ANGLE_EPSILON = 1 / 16.0;

        int basePort = 400 + aDeviceId * 3;

        int yawPort = basePort + 0;
        int pitchPort = basePort + 1;
        int rollPort = basePort + 2;
        int xPort = basePort + 0;
        int yPort = basePort + 1;
        int zPort = basePort + 2;

        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(yawPort));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(pitchPort));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(rollPort));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().contains(xPort));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().contains(yPort));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList().contains(zPort));

        double[] rawAngles = new double[3];
        double[] yawPitchRollAngles = new double[3];
        FusionStatus fusionStatus = new FusionStatus();

        aImu.getRawGyro(rawAngles);
        aImu.getFusedHeading(fusionStatus);
        aImu.getYawPitchRoll(yawPitchRollAngles);
        Assertions.assertEquals(0, fusionStatus.heading, ANGLE_EPSILON);
        Assertions.assertEquals(0, rawAngles[0], ANGLE_EPSILON);
        Assertions.assertEquals(0, rawAngles[1], ANGLE_EPSILON);
        Assertions.assertEquals(0, rawAngles[2], ANGLE_EPSILON);
        Assertions.assertEquals(0, yawPitchRollAngles[0], ANGLE_EPSILON);
        Assertions.assertEquals(0, yawPitchRollAngles[1], ANGLE_EPSILON);
        Assertions.assertEquals(0, yawPitchRollAngles[2], ANGLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(yawPort), ANGLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(pitchPort), ANGLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(rollPort), ANGLE_EPSILON);

        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(yawPort, 47);
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(pitchPort, -98);
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(rollPort, 24);

        aImu.getRawGyro(rawAngles);
        aImu.getFusedHeading(fusionStatus);
        aImu.getYawPitchRoll(yawPitchRollAngles);
        Assertions.assertEquals(47, fusionStatus.heading, ANGLE_EPSILON);
        Assertions.assertEquals(47, rawAngles[0], ANGLE_EPSILON);
        Assertions.assertEquals(-98, rawAngles[1], ANGLE_EPSILON);
        Assertions.assertEquals(24, rawAngles[2], ANGLE_EPSILON);
        Assertions.assertEquals(47, yawPitchRollAngles[0], ANGLE_EPSILON);
        Assertions.assertEquals(-98, yawPitchRollAngles[1], ANGLE_EPSILON);
        Assertions.assertEquals(24, yawPitchRollAngles[2], ANGLE_EPSILON);
        Assertions.assertEquals(47, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(yawPort), ANGLE_EPSILON);
        Assertions.assertEquals(-98, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(pitchPort), ANGLE_EPSILON);
        Assertions.assertEquals(24, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(rollPort), ANGLE_EPSILON);
    }
}
