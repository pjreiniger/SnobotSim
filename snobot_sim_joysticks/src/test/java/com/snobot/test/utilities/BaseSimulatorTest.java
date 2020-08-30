package com.snobot.test.utilities;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpiutil.RuntimeDetector;

public class BaseSimulatorTest
{
    protected static final double DOUBLE_EPSILON = .00001;

    protected final void delete(File aPath)
    {
        File[] l = aPath.listFiles();
        Assertions.assertNotNull(l);
        for (File f : l)
        {
            if (f.isDirectory())
            {
                delete(f);
            }
            else
            {
                Assertions.assertTrue(f.delete());
            }
        }
        Assertions.assertTrue(aPath.delete());
    }

    @BeforeEach
    public void setup()
    {

    }

    @AfterEach
    public void cleanup()
    {
        // Nothing to do
        if (!RuntimeDetector.isWindows())
        {
            DriverStation.getInstance().release();
        }
    }
}
