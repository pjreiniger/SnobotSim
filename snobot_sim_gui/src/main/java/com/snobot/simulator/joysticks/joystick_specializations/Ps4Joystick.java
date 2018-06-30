package com.snobot.simulator.joysticks.joystick_specializations;

import java.util.Arrays;

import com.snobot.simulator.joysticks.BaseJoystick;

import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;

public class Ps4Joystick extends BaseJoystick
{
    private static final Identifier[] sAXIS = new Identifier[]
    {
        Identifier.Axis.X, // Left x
        Identifier.Axis.Y, // Left Y
        Identifier.Axis.RX, // Left Trigger
        Identifier.Axis.RY, // Right Trigger
        Identifier.Axis.Z, // Right x
        Identifier.Axis.RZ // Right x
    };

    private static final Identifier[] sBUTTONS = new Identifier[]
    {
        Identifier.Button._1, // X
        Identifier.Button._2, // Circle
        Identifier.Button._0, // Square
        Identifier.Button._3, // Triangle
        Identifier.Button._4, // L1
        Identifier.Button._5, // R1
        Identifier.Button._8, // Share
        Identifier.Button._9, // Options
        Identifier.Button._10, // L3
        Identifier.Button._11, // R3
        Identifier.Button._6, // L2 (half pressed or more)
        Identifier.Button._7, // R2 (half pressed or more)
        Identifier.Button._12, // ps4 button
        Identifier.Button._13, // Motion pad
        Identifier.Button._12, // ps4 button
    };

    private static final Identifier[] sPOV = new Identifier[]
    {
        Identifier.Axis.POV
    };

    public Ps4Joystick(Controller aController)
    {
        this(aController, "PS4");
    }

    public Ps4Joystick(Controller aController, String aName)
    {
        super(aName, aController, Arrays.asList(sAXIS), Arrays.asList(sBUTTONS), Arrays.asList(sPOV));
    }
}
