package com.snobot.simulator.simulator_components.navx;

import com.kauailabs.navx.frc.AHRS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJavaTest;

import edu.wpi.first.wpilibj.I2C;

@Tag("NavX")
public class TestI2CNavx extends BaseSimulatorJavaTest
{
    private static final String sNAVX_TYPE = "NavX";

    @SuppressWarnings("PMD.UnusedLocalVariable")
    @Test
    public void testConstruction()
    {
        // Port = 0
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        DataAccessorFactory.getInstance().getI2CAccessor().createI2CSimulator(0, sNAVX_TYPE);

        final AHRS navxOnboard = new AHRS(I2C.Port.kOnboard);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(250));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(251));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(252));
        navxOnboard.close();

        // Port = 1
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        DataAccessorFactory.getInstance().getI2CAccessor().createI2CSimulator(1, sNAVX_TYPE);

        final AHRS navxMxp = new AHRS(I2C.Port.kMXP);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(253));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(254));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getPortList().contains(255));
        navxMxp.close();
    }

    @Test
    public void testI2CNavx() throws InterruptedException
    {
        String osName = System.getProperty("os.name").toLowerCase(); // NOPMD
        boolean isMacOs = osName.startsWith("mac os x");
        if (isMacOs)
        {
            return;
        }

        DataAccessorFactory.getInstance().getI2CAccessor().createI2CSimulator(0, sNAVX_TYPE);

        final int sleepTime = 100;
        AHRS navx = new AHRS(I2C.Port.kOnboard);
        navx.enableLogging(true);

        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getPortList().size());
        Thread.sleep(500);

        int yawHandle = 250;
        int pitchHandle = 251;
        int rollHandle = 252;
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
        Assertions.assertEquals(179, navx.getYaw(), 2); // TODO fix mac builds
        Assertions.assertEquals(700, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(-470, navx.getRoll(), DOUBLE_EPSILON);
    }
}
