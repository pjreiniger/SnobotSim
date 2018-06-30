package com.snobot.simulator.gui.joysticks.sub_panels;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.BaseGuiSimulatorTest;
import com.snobot.test.utilities.MockIJoystick;

public class TestXboxPanel extends BaseGuiSimulatorTest
{
    @Test
    public void testJoystickManagerDialog() throws Exception
    {
        JFrame frame = getFrame();

        MockIJoystick joystick = new MockIJoystick();
        XboxPanel panel = new XboxPanel();
        panel.setJoystick(joystick);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.pack();

        updateDisplay(panel);

        for (int i = 0; i < joystick.mButtonValues.length; ++i)
        {
            joystick.mButtonValues[i] = true;
        }
        updateDisplay(panel);

        for (int i = 0; i < joystick.mAxisValues.length; ++i)
        {
            joystick.mAxisValues[i] = 1;
        }
        updateDisplay(panel);

        updatePov(panel, joystick, 45);
        updatePov(panel, joystick, 90);
        updatePov(panel, joystick, 135);
        updatePov(panel, joystick, 180);
        updatePov(panel, joystick, 225);
        updatePov(panel, joystick, 270);
        updatePov(panel, joystick, -45);
        updatePov(panel, joystick, -1);
        updatePov(panel, joystick, 69);
    }

    private void updatePov(XboxPanel aPanel, MockIJoystick aJoystick, int aPov) throws InterruptedException
    {

        for (int i = 0; i < aJoystick.mPovValues.length; ++i)
        {
            aJoystick.mPovValues[i] = (short) aPov;
        }
        updateDisplay(aPanel);
    }

    private void updateDisplay(XboxPanel aPanel) throws InterruptedException
    {
        sleepForVisualDebugging(100);
        aPanel.updateDisplay();

    }
}
