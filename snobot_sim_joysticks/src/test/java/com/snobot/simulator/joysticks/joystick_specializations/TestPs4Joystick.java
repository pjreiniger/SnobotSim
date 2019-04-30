package com.snobot.simulator.joysticks.joystick_specializations;

import org.junit.jupiter.api.Test;

import com.snobot.test.utilities.MockController;

public class TestPs4Joystick
{
    @Test
    public void testKeyboardJoystick() throws Exception
    {
        Ps4Joystick joystick = new Ps4Joystick(new MockController());

        float[] axisValues = joystick.getAxisValues();
        short[] povValues = joystick.getPovValues();
        int buttonMask = joystick.getButtonMask();
    }
}
