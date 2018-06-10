package com.snobot.test.utilities;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.snobot.simulator.jni.SnobotSimulatorJni;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.jni.JniDataAccessor;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;

public class BaseSimulatorJniTest
{
    private static boolean INITIALIZED = false;
    protected static final double DOUBLE_EPSILON = .0002;

    private void delete(File aPath)
    {
        File[] files = aPath.listFiles();
        if (files == null)
        {
            return;
        }

        for (File f : files)
        {
            if (f.isDirectory())
            {
                delete(f);
            }
            else
            {
                if (!f.delete())
                {
                    LogManager.getLogger().log(Level.WARN, "Could not delete file " + f);
                }
            }
        }
        if (!aPath.delete())
        {
            LogManager.getLogger().log(Level.WARN, "Could not delete file " + aPath);
        }
    }

    @BeforeEach
    public void setup()
    {
        if (!INITIALIZED)
        {
            DataAccessorFactory.setAccessor(new JniDataAccessor());
            SnobotSimulatorJni.initializeLogging(1);

            File directory = new File("test_output");
            if (directory.exists())
            {
                delete(directory);
            }
            if (!directory.mkdirs())
            {
                LogManager.getLogger().log(Level.WARN, "Could not make directory at " + directory);
            }
        }

        SnobotSimulatorJni.reset();
        RobotBase.initializeHardwareConfiguration();
    }

    protected void simulateForTime(double aSeconds, Runnable aTask)
    {
        simulateForTime(aSeconds, .02, aTask);
    }

    protected void simulateForTime(double aSeconds, double aUpdatePeriod, Runnable aTask)
    {
        double updateFrequency = 1 / aUpdatePeriod;

        for (int i = 0; i < updateFrequency * aSeconds; ++i)
        {
            aTask.run();
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().updateSimulatorComponents(aUpdatePeriod);
        }
    }

    @AfterEach
    public void shutdown()
    {
        DriverStation.getInstance().release();
    }
}
