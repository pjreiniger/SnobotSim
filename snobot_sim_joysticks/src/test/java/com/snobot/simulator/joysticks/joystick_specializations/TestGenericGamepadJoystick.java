package com.snobot.simulator.joysticks.joystick_specializations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.MockController;

public class TestGenericGamepadJoystick
{
    @Test
    public void testKeyboardJoystick() throws Exception
    {
        GenericGamepadJoystick joystick = new GenericGamepadJoystick(new MockController());

        float[] axisValues = joystick.getAxisValues();
        short[] povValues = joystick.getPovValues();
        int buttonMask = joystick.getButtonMask();

        Assertions.assertEquals(0, axisValues.length);
        Assertions.assertEquals(0, povValues.length);
        Assertions.assertEquals(0, buttonMask);
    }
}
