package com.snobot.simulator.simulator_components.navx;

import com.snobot.simulator.module_wrapper.interfaces.IGyroWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.kauailabs.navx.frc.AHRS;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.test.utilities.BaseSimulatorJniTest;
import com.snobot.test.utilities.SimDeviceDumpHelper;

import edu.wpi.first.wpilibj.I2C;

@Disabled
@Tag("NavX")
public class TestI2CNavx extends BaseSimulatorJniTest
{
    private static final String sNAVX_TYPE = "NavX";

    @SuppressWarnings("PMD.UnusedLocalVariable")
    @Test
    public void testConstruction()
    {
        // Port = 0
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        DataAccessorFactory.getInstance().getI2CAccessor().createI2CSimulator(0, sNAVX_TYPE);

        final AHRS navxOnboard = new AHRS(I2C.Port.kOnboard);
        SimDeviceDumpHelper.dumpSimDevices();
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(250));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(251));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(252));
        navxOnboard.close();

        // Port = 1
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        Assertions.assertEquals(0, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        DataAccessorFactory.getInstance().getI2CAccessor().createI2CSimulator(1, sNAVX_TYPE);

        final AHRS navxMxp = new AHRS(I2C.Port.kMXP);
        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(253));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(254));
        Assertions.assertTrue(DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().containsKey(255));
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

        Assertions.assertEquals(3, DataAccessorFactory.getInstance().getGyroAccessor().getWrappers().size());
        Thread.sleep(500);

        int yawHandle = 250;
        int pitchHandle = 251;
        int rollHandle = 252;

        IGyroWrapper yawWrapper = DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(yawHandle);
        IGyroWrapper pitchWrapper = DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(pitchHandle);
        IGyroWrapper rollWrapper = DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(rollHandle);

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
        Assertions.assertEquals(179, navx.getYaw(), 2); // TODO fix mac builds
        Assertions.assertEquals(700, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(-470, navx.getRoll(), DOUBLE_EPSILON);
    }
}
