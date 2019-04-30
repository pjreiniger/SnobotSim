package com.snobot.simulator.joysticks.joystick_specializations;

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
    }
}
