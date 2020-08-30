package com.snobot.simulator.joysticks.joystick_specializations;

import com.snobot.simulator.joysticks.BaseJoystick;
import net.java.games.input.Component;
import net.java.games.input.Controller;

import java.util.Arrays;

public class LogitechDualAction extends BaseJoystick
{
    private static final Component.Identifier[] sAXIS = new Component.Identifier[]
    {
        Component.Identifier.Axis.X, // Left x
        Component.Identifier.Axis.Y, // Left Y
        Component.Identifier.Axis.RX_ACCELERATION, // Left Trigger
        Component.Identifier.Axis.RX_ACCELERATION, // Right Trigger
        Component.Identifier.Axis.Z, // Right x
        Component.Identifier.Axis.RZ, // Right y
    };

    private static final Component.Identifier[] sBUTTONS = new Component.Identifier[]
    {
        Component.Identifier.Button._1, // A
        Component.Identifier.Button._2, // B
        Component.Identifier.Button._0, // X
        Component.Identifier.Button._3, // Y
        Component.Identifier.Button._4, // LB
        Component.Identifier.Button._5, // RB
        Component.Identifier.Button._8, // back
        Component.Identifier.Button._9, // start
        Component.Identifier.Button._10, // L3
        Component.Identifier.Button._11, // R3
    };

    private static final Component.Identifier[] sPOV = new Component.Identifier[]
    {
        Component.Identifier.Axis.POV
    };

    public LogitechDualAction(Controller aController)
    {
        this(aController, "Logitech Dual Action");
    }

    public LogitechDualAction(Controller aController, String aName)
    {
        super(aName, aController, Arrays.asList(sAXIS), Arrays.asList(sBUTTONS), Arrays.asList(sPOV));
    }

    @Override
    public float[] getAxisValues()
    {
        float[] output = super.getAxisValues();

        output[2] = mController.getComponent(Component.Identifier.Button._6).getPollData();
        output[3] = mController.getComponent(Component.Identifier.Button._7).getPollData();

        return output;
    }
}
