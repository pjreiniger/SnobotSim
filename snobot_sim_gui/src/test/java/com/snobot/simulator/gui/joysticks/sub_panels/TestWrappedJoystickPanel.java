package com.snobot.simulator.gui.joysticks.sub_panels;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.BaseGuiSimulatorTest;
import com.snobot.test.utilities.MockIJoystick;

public class TestWrappedJoystickPanel extends BaseGuiSimulatorTest
{
    @Test
    public void testJoystickManagerDialog() throws Exception
    {
        JFrame frame = getFrame();

        MockIJoystick joystick = new MockIJoystick();
        WrappedJoystickPanel panel = new WrappedJoystickPanel();
        panel.setJoystick(joystick);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.pack();

        panel.updateDisplay();

    }
}
