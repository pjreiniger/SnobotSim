package com.snobot.simulator.gui;

import javafx.scene.paint.Color;

/**
 *
 * @author PJ
 */
public final class Util
{
    private Util()
    {
        // Class is static helper, don't construct it
    }

    public static Color getMotorColor(double aSpeed)
    {
        return colorGetShadedColor(aSpeed, 1, -1);
    }

    public static Color colorGetShadedColor(double aSpeed, double aMax, double aMin) // NOPMD
    {
        if (Double.isNaN(aSpeed))
        {
            aSpeed = 0;
        }
        if (aSpeed > aMax)
        {
            aSpeed = aMax;
        }
        else if (aSpeed < aMin)
        {
            aSpeed = aMin;
        }

        double percent = (aSpeed - aMin) / (aMax - aMin);
        double hue = percent * 120;
        double saturation = 1;
        double brightness = 1;

        return Color.hsb(hue, saturation, brightness);
    }
}
