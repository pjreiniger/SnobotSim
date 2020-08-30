package com.snobot.simulator.joysticks.joystick_specializations;

import org.junit.jupiter.api.Assertions;
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

        Assertions.assertEquals(6, axisValues.length);
        Assertions.assertEquals(1, povValues.length);
        Assertions.assertEquals(0, buttonMask);
    }
}
