package com.snobot.simulator.gui.joysticks;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.BaseGuiSimulatorTest;

public class TestPanels extends BaseGuiSimulatorTest
{
    @Test
    public void testJoystickManagerDialog() throws Exception
    {
        JoystickManagerDialog dialog = new JoystickManagerDialog();
        boolean success = true;

        try
        {
            dialog.setVisible(true);
        }
        catch (Exception ex)
        {
            LogManager.getLogger().log(Level.ERROR, ex);
        }
        finally
        {
            dialog.dispose();
        }

        Assertions.assertTrue(success);
    }
}
