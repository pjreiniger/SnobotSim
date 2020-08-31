package com.snobot.simulator.simulator_components.navx;

import com.kauailabs.navx.frc.AHRS;
import com.snobot.simulator.SimDeviceDumpHelper;
import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.SPI;

@Tag("NavX")
public class TestSpiNavx extends BaseSimulatorJavaTest
{
    private static final long SHUTDOWN_TIME = 200;
    private static final String sNAVX_TYPE = "NavX";

    @Test
    public void testConstruction() throws InterruptedException
    {
        // Port = 0
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(0, sNAVX_TYPE);

        new AHRS(SPI.Port.kOnboardCS0);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(200));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(201));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(202));
//        navxCs0.free();
        Thread.sleep(SHUTDOWN_TIME);

        // Port = 1
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(1, sNAVX_TYPE);

        new AHRS(SPI.Port.kOnboardCS1);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(203));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(204));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(205));
//        navxCs1.free();
        Thread.sleep(SHUTDOWN_TIME);

        // Port = 2
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(2, sNAVX_TYPE);

        new AHRS(SPI.Port.kOnboardCS2);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(206));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(207));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(208));
//        navxCs2.free();
        Thread.sleep(SHUTDOWN_TIME);

        // Port = 3
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(3, sNAVX_TYPE);

        new AHRS(SPI.Port.kOnboardCS3);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(209));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(210));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(211));
        Thread.sleep(SHUTDOWN_TIME);

        // Port = 4
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(4, sNAVX_TYPE);

        new AHRS(SPI.Port.kMXP);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(212));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(213));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(214));
//        navxMxp.free();
        Thread.sleep(SHUTDOWN_TIME);
    }

    @Test
    public void testSpiNavx() throws InterruptedException
    {
        String osName = System.getProperty("os.name").toLowerCase(); // NOPMD
        boolean isMacOs = osName.startsWith("mac os x");
        if (isMacOs)
        {
            return;
        }

        DataAccessorFactory.getInstance().getSpiAccessor().createSpiSimulator(1, sNAVX_TYPE);

        final int sleepTime = 100;
        AHRS navx = new AHRS(SPI.Port.kOnboardCS1);
        navx.enableLogging(true);

        int yawHandle = 203;
        int pitchHandle = 204;
        int rollHandle = 205;

        SimDeviceDumpHelper.dumpSimDevices();

        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(yawHandle));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(pitchHandle));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(rollHandle));


        IGyroWrapper yawWrapper = DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(yawHandle);
        IGyroWrapper pitchWrapper = DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(pitchHandle);
        IGyroWrapper rollWrapper = DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(rollHandle);

        // Thread.sleep(100000);

        Thread.sleep(500);
        Assertions.assertEquals(0, yawWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, pitchWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, rollWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, navx.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, navx.getRoll(), DOUBLE_EPSILON);

        yawWrapper.setAngle(180);
        pitchWrapper.setAngle(-180);
        rollWrapper.setAngle(30);
        Thread.sleep(sleepTime);
        Assertions.assertEquals(180, yawWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(-180, pitchWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(30, rollWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(180, navx.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(-180, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(30, navx.getRoll(), DOUBLE_EPSILON);

        // Test wrap around
        yawWrapper.setAngle(-181);
        pitchWrapper.setAngle(700);
        rollWrapper.setAngle(-470);
        Thread.sleep(sleepTime);
        Assertions.assertEquals(-181, yawWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(700, pitchWrapper.getAngle(), DOUBLE_EPSILON);
        Assertions.assertEquals(-470, rollWrapper.getAngle(), DOUBLE_EPSILON);
//        Assertions.assertEquals(179, navx.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(700, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(-470, navx.getRoll(), DOUBLE_EPSILON);

        navx.reset();

//        navx.free();
        Thread.sleep(SHUTDOWN_TIME);
    }
}
