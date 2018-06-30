package com.snobot.simulator.gui;

import java.awt.Color;

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

        float percent = (float) ((aSpeed - aMin) / (aMax - aMin));
        float hue = percent * .33f;
        float saturation = 1f;
        float brightness = 1f;

        return Color.getHSBColor(hue, saturation, brightness);
    }

    /**
     * Modifies the color to make it more see through
     *
     * @param aColor
     *            The color to modify
     * @param aAlpha
     *            The new alpha value (0-255, 0 being full see-through)
     * @return The modified color
     */
    public static Color alphaColor(Color aColor, int aAlpha)
    {
        return new Color(aColor.getRed(), aColor.getGreen(), aColor.getBlue(), aAlpha);
    }
}
