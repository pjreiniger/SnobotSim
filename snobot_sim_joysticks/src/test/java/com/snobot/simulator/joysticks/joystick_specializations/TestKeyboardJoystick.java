package com.snobot.simulator.joysticks.joystick_specializations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.snobot.simulator.joysticks.IMockJoystick;
import com.snobot.test.utilities.MockController;

import net.java.games.input.Component.Identifier;

public class TestKeyboardJoystick
{
    @Test
    public void testKeyboardJoystick() throws Exception
    {
        MockController controller = new MockController();
        KeyboardJoystick joystick = new KeyboardJoystick(controller);

        short[] povValues = joystick.getPovValues();
        Assertions.assertEquals(-1, povValues[0]);

        testAxis(joystick, 0, 0, 1, 1, 0, 0);

        // Test Axis
        reset(controller);
        controller.setValueForComponent(Identifier.Key.W, 1);
        testAxis(joystick, 0, -1, 1, 1, 0, 0);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.S, 1);
        testAxis(joystick, 0, 1, 1, 1, 0, 0);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.A, 1);
        testAxis(joystick, -1, 0, 1, 1, 0, 0);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.D, 1);
        testAxis(joystick, 1, 0, 1, 1, 0, 0);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.I, 1);
        testAxis(joystick, 0, 0, 1, 1, 0, -1);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.K, 1);
        testAxis(joystick, 0, 0, 1, 1, 0, 1);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.J, 1);
        testAxis(joystick, 0, 0, 1, 1, -1, 0);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.L, 1);
        testAxis(joystick, 0, 0, 1, 1, 1, 0);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.C, 1);
        testAxis(joystick, 0, 0, -1, 1, 0, 0);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.N, 1);
        testAxis(joystick, 0, 0, 1, -1, 0, 0);

        // Set POV
        reset(controller);
        testPovs(joystick, -1);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.UP, 1);
        testPovs(joystick, 0);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.UP, 1);
        controller.setValueForComponent(Identifier.Key.RIGHT, 1);
        testPovs(joystick, 45);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.RIGHT, 1);
        testPovs(joystick, 90);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.RIGHT, 1);
        controller.setValueForComponent(Identifier.Key.DOWN, 1);
        testPovs(joystick, 135);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.DOWN, 1);
        testPovs(joystick, 180);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.DOWN, 1);
        controller.setValueForComponent(Identifier.Key.LEFT, 1);
        testPovs(joystick, 225);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.LEFT, 1);
        testPovs(joystick, 270);

        reset(controller);
        controller.setValueForComponent(Identifier.Key.UP, 1);
        controller.setValueForComponent(Identifier.Key.LEFT, 1);
        testPovs(joystick, -45);
    }

    private void reset(MockController aController)
    {
        aController.setValueForComponent(Identifier.Key.W, 0);
        aController.setValueForComponent(Identifier.Key.A, 0);
        aController.setValueForComponent(Identifier.Key.S, 0);
        aController.setValueForComponent(Identifier.Key.D, 0);

        aController.setValueForComponent(Identifier.Key.I, 0);
        aController.setValueForComponent(Identifier.Key.J, 0);
        aController.setValueForComponent(Identifier.Key.K, 0);
        aController.setValueForComponent(Identifier.Key.L, 0);

        aController.setValueForComponent(Identifier.Key.C, 0);
        aController.setValueForComponent(Identifier.Key.N, 0);

        aController.setValueForComponent(Identifier.Key.UP, 0);
        aController.setValueForComponent(Identifier.Key.DOWN, 0);
        aController.setValueForComponent(Identifier.Key.LEFT, 0);
        aController.setValueForComponent(Identifier.Key.RIGHT, 0);
    }

    private void testAxis(IMockJoystick aJoystick, double aAxis0, double aAxis1, double aAxis2, double aAxis3, double aAxis4, double aAxis5)
    {
        float[] axisValues = aJoystick.getAxisValues();

        Assertions.assertEquals(aAxis0, axisValues[0], .0001);
        Assertions.assertEquals(aAxis1, axisValues[1], .0001);
        Assertions.assertEquals(aAxis2, axisValues[2], .0001);
        Assertions.assertEquals(aAxis3, axisValues[3], .0001);
        Assertions.assertEquals(aAxis4, axisValues[4], .0001);
        Assertions.assertEquals(aAxis5, axisValues[5], .0001);
    }

    private void testPovs(IMockJoystick aJoystick, int aPov0)
    {
        aJoystick.getController().poll();
        short[] povValues = aJoystick.getPovValues();

        Assertions.assertEquals(aPov0, povValues[0]);
    }
}
