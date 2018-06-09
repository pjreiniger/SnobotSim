package com.kauailabs.navx.frc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.JniLibraryResourceLoader;
import com.snobot.simulator.navx.I2CNavxSimulator;

import edu.wpi.first.hal.sim.mockdata.DriverStationDataJNI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.HLUsageReporting;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.internal.HardwareHLUsageReporting;
import edu.wpi.first.wpilibj.internal.HardwareTimer;

@Tag("NavX")
public class TestNavx
{
    private static final long SHUTDOWN_TIME = 50;
    private static final String sNAVX_TYPE = "NavX";

    @Test
    public void testI2CNavx() throws InterruptedException
    {
        initialize();

        I2C.Port port = I2C.Port.kOnboard;
        I2CNavxSimulator sim = new I2CNavxSimulator(port.value);

        AHRS navx = new AHRS(port);
        navx.enableLogging(true);

        Thread.sleep(500);

        testNavx(sim, navx);

        DriverStationDataJNI.notifyNewData();
        DriverStation.getInstance().release();
    }

    private void initialize()
    {
        JniLibraryResourceLoader.loadLibrary("wpiutil");
        JniLibraryResourceLoader.loadLibrary("wpiHal");
        JniLibraryResourceLoader.loadLibrary("halsim_adx_gyro_accelerometer");
        JniLibraryResourceLoader.loadLibrary("navx_simulator");
        JniLibraryResourceLoader.loadLibrary("navx_simulator_jni");

        if (!HAL.initialize(500, 0))
        {
            throw new IllegalStateException("Failed to initialize. Terminating");
        }

        // Set some implementations so that the static methods work properly
        Timer.SetImplementation(new HardwareTimer());
        HLUsageReporting.SetImplementation(new HardwareHLUsageReporting());
    }

    private void testNavx(I2CNavxSimulator sim, AHRS navx) throws InterruptedException
    {
        final int sleepTime = 100;
        final double DOUBLE_EPSILON = .0001;

        Assertions.assertEquals(0, sim.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, sim.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, sim.getRoll(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, navx.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(0, navx.getRoll(), DOUBLE_EPSILON);

        sim.setYaw(180);
        sim.setPitch(-180);
        sim.setRoll(30);
        Thread.sleep(sleepTime);

        Assertions.assertEquals(180, sim.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(-180, sim.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(30, sim.getRoll(), DOUBLE_EPSILON);
        Assertions.assertEquals(180, navx.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(-180, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(30, navx.getRoll(), DOUBLE_EPSILON);

        // Test wrap around
        sim.setYaw(-181);
        sim.setPitch(700);
        sim.setRoll(-470);
        Thread.sleep(sleepTime);
        Assertions.assertEquals(-181, sim.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(700, sim.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(-470, sim.getRoll(), DOUBLE_EPSILON);
        Assertions.assertEquals(179, navx.getYaw(), DOUBLE_EPSILON);
        Assertions.assertEquals(-20, navx.getPitch(), DOUBLE_EPSILON);
        Assertions.assertEquals(-110, navx.getRoll(), DOUBLE_EPSILON);
    }
}
