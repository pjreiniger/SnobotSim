package com.snobot.simulator.joysticks.joystick_specializations;

import java.util.Arrays;

import com.snobot.simulator.joysticks.BaseJoystick;

import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;

public class XboxJoystick extends BaseJoystick
{
    private static final Identifier[] sAXIS = new Identifier[]
    {
        Identifier.Axis.X, // Left x
        Identifier.Axis.Y, // Left Y
        Identifier.Axis.Z, // Left Trigger
        Identifier.Axis.Z, // Right Trigger
        Identifier.Axis.RX, // Right x
        Identifier.Axis.RY, // Right y
    };

    private static final Identifier[] sBUTTONS = new Identifier[]
    {
        Identifier.Button._0, // A
        Identifier.Button._1, // B
        Identifier.Button._2, // X
        Identifier.Button._3, // Y
        Identifier.Button._4, // LB
        Identifier.Button._5, // RB
        Identifier.Button._6, // windows/back
        Identifier.Button._7, // lines/start
        Identifier.Button._8, // Left Joystick In
        Identifier.Button._9, // Right Joystick In
    };

    private static final Identifier[] sPOV = new Identifier[]
    {
        Identifier.Axis.POV
    };

    public XboxJoystick(Controller aController)
    {
        this(aController, "XBOX");
    }

    public XboxJoystick(Controller aController, String aName)
    {
        super(aName, aController, Arrays.asList(sAXIS), Arrays.asList(sBUTTONS), Arrays.asList(sPOV));
    }

    @Override
    public float[] getAxisValues()
    {
        float[] output = super.getAxisValues();
        float triggerValue = output[2];

        if (triggerValue < 0)
        {
            output[2] = 0;
            output[3] = triggerValue;
        }
        else
        {
            output[2] = triggerValue;
            output[3] = 0;
        }


        return output;
    }
}
