package com.snobot.simulator.simulator_components.navx;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kauailabs.navx.frc.AHRS;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;

import edu.wpi.first.wpilibj.SPI;

public class TestSpiNavx extends BaseSimulatorJniTest
{
    private static final long SHUTDOWN_TIME = 200;
    private static final String sNAVX_TYPE = "NavX";

    @Test
    public void testConstruction() throws InterruptedException
    {
        // Port = 0
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(0, sNAVX_TYPE);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());

        final AHRS navxCs0 = new AHRS(SPI.Port.kOnboardCS0);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(200));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(201));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(202));
        navxCs0.free();
        Thread.sleep(SHUTDOWN_TIME);

        // Port = 1
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(1, sNAVX_TYPE);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());

        final AHRS navxCs1 = new AHRS(SPI.Port.kOnboardCS1);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(203));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(204));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(205));
        navxCs1.free();
        Thread.sleep(SHUTDOWN_TIME);

        // Port = 2
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(2, sNAVX_TYPE);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());

        final AHRS navxCs2 = new AHRS(SPI.Port.kOnboardCS2);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(206));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(207));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(208));
        navxCs2.free();
        Thread.sleep(SHUTDOWN_TIME);

        // Port = 3
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(3, sNAVX_TYPE);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());

        final AHRS navxCs3 = new AHRS(SPI.Port.kOnboardCS3);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(209));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(210));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(211));
        navxCs3.free();
        Thread.sleep(SHUTDOWN_TIME);

        // Port = 4
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(4, sNAVX_TYPE);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());

        final AHRS navxMxp = new AHRS(SPI.Port.kMXP);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(212));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(213));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(214));
        navxMxp.free();
        Thread.sleep(SHUTDOWN_TIME);
    }

    @Test
    public void testSpiNavx() throws InterruptedException
    {
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(1, sNAVX_TYPE);

        final int sleepTime = 100;
        AHRS navx = new AHRS(SPI.Port.kOnboardCS1);
        navx.enableLogging(true);

        int yawHandle = 203;
        int pitchHandle = 204;
        int rollHandle = 205;

        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(yawHandle));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(pitchHandle));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(rollHandle));

        // Thread.sleep(100000);

        Thread.sleep(500);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(yawHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(pitchHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(rollHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(0, navx.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, navx.getRoll(), DOUBLE_EPSILON);

        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(yawHandle, 180);
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(pitchHandle, -180);
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(rollHandle, 30);
        Thread.sleep(sleepTime);
        Assertions.assertEquals(180, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(yawHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(-180, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(pitchHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(30, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(rollHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(180, navx.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(-180, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(30, navx.getRoll(), DOUBLE_EPSILON);

        // Test wrap around
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(yawHandle, -181);
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(pitchHandle, 700);
        DataAccessorFactory.getInstance().getGyroAccessor().setAngle(rollHandle, -470);
        Thread.sleep(sleepTime);
        Assertions.assertEquals(-181, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(yawHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(700, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(pitchHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(-470, DataAccessorFactory.getInstance().getGyroAccessor().getAngle(rollHandle), DOUBLE_EPSILON);
        Assertions.assertEquals(179, navx.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(-20, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(-110, navx.getRoll(), DOUBLE_EPSILON);

        navx.reset();

        navx.free();
        Thread.sleep(SHUTDOWN_TIME);
    }
}
