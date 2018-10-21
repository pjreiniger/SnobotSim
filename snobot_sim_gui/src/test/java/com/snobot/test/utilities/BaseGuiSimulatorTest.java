package com.snobot.test.utilities;

import javax.swing.JFrame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseGuiSimulatorTest extends BaseSimulatorTest
{
    private JFrame mFrame;

    protected JFrame getFrame()
    {
        if (mFrame == null)
        {
            mFrame = new JFrame("Test Frame");
        }

        return mFrame;
    }

    @Override
    @BeforeEach
    public void setup()
    {
        super.setup();
    }

    @Override
    @AfterEach
    public void cleanup()
    {
        super.cleanup();

        if (mFrame != null)
        {
            mFrame.dispose();
        }
    }

    protected void sleepForVisualDebugging()
    {
        sleepForVisualDebugging(1000);
    }

    protected void sleepForVisualDebugging(long aMilliseconds)
    {
        // try
        // {
        // Thread.sleep(milliseconds);
        // }
        // catch (Exception e)
        // {
        //
        // }
    }

}
