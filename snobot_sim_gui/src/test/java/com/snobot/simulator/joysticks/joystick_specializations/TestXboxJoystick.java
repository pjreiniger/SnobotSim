package com.snobot.simulator.joysticks.joystick_specializations;

import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.MockController;

public class TestXboxJoystick
{
    @Test
    public void testKeyboardJoystick() throws Exception
    {
        XboxJoystick joystick = new XboxJoystick(new MockController());

        float[] axisValues = joystick.getAxisValues();
        short[] povValues = joystick.getPovValues();
        int buttonMask = joystick.getButtonMask();
    }
}
