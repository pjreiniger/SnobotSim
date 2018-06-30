package com.snobot.simulator.joysticks.joystick_specializations;

import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.gui.joysticks.sub_panels.XboxButtonMap;
import com.snobot.simulator.joysticks.BaseJoystick;

import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;

public class KeyboardJoystick extends BaseJoystick
{
    private static final Logger sLOGGER = LogManager.getLogger(KeyboardJoystick.class);

    // Backwards on purpose
    private static final short sNEGATIVE_VALUE = 1;
    private static final short sPOSITIVE_VALUE = -1;

    private static final Identifier[] sAXIS = new Identifier[]
    {
        null,
        null,
        null,
        null,
        null,
        null
    };

    private static final Identifier[] sBUTTONS = new Identifier[]
    {
        Identifier.Key._1,
        Identifier.Key._2,
        Identifier.Key._3,
        Identifier.Key._4,
        Identifier.Key._5,
        Identifier.Key._6,
        Identifier.Key._7,
        Identifier.Key._8,
        Identifier.Key._9,
        Identifier.Key._0,
    };

    private static final Identifier[] sPOV = new Identifier[] {
        Identifier.Key._0,
    };

    public KeyboardJoystick(Controller aController)
    {
        this(aController, "Keyboard");
    }

    public KeyboardJoystick(Controller aController, String aName)
    {
        super(aName, aController, Arrays.asList(sAXIS), Arrays.asList(sBUTTONS), Arrays.asList(sPOV));
    }

    @Override
    public float[] getAxisValues()
    {
        if (mController == null)
        {
            sLOGGER.log(Level.ERROR, "Controller is null.  The simulator could not setup a controller of type [" + mName + "]");
        }
        else
        {
            mController.poll();

            // Left Y
            if (mController.getComponent(Identifier.Key.W).getPollData() != 0) // NOPMD
            {
                mAxisValues[XboxButtonMap.LEFT_Y_AXIS] = sPOSITIVE_VALUE;
            }
            else if (mController.getComponent(Identifier.Key.S).getPollData() != 0) // NOPMD
            {
                mAxisValues[XboxButtonMap.LEFT_Y_AXIS] = sNEGATIVE_VALUE;
            }
            else
            {
                mAxisValues[XboxButtonMap.LEFT_Y_AXIS] = 0;
            }

            // Left X
            if (mController.getComponent(Identifier.Key.A).getPollData() != 0) // NOPMD
            {
                mAxisValues[XboxButtonMap.LEFT_X_AXIS] = sPOSITIVE_VALUE;
            }
            else if (mController.getComponent(Identifier.Key.D).getPollData() != 0) // NOPMD
            {
                mAxisValues[XboxButtonMap.LEFT_X_AXIS] = sNEGATIVE_VALUE;
            }
            else
            {
                mAxisValues[XboxButtonMap.LEFT_X_AXIS] = 0;
            }

            // Right Y
            if (mController.getComponent(Identifier.Key.I).getPollData() != 0) // NOPMD
            {
                mAxisValues[XboxButtonMap.RIGHT_Y_AXIS] = sPOSITIVE_VALUE;
            }
            else if (mController.getComponent(Identifier.Key.K).getPollData() != 0) // NOPMD
            {
                mAxisValues[XboxButtonMap.RIGHT_Y_AXIS] = sNEGATIVE_VALUE;
            }
            else
            {
                mAxisValues[XboxButtonMap.RIGHT_Y_AXIS] = 0;
            }

            // Right X
            if (mController.getComponent(Identifier.Key.J).getPollData() != 0) // NOPMD
            {
                mAxisValues[XboxButtonMap.RIGHT_X_AXIS] = sPOSITIVE_VALUE;
            }
            else if (mController.getComponent(Identifier.Key.L).getPollData() != 0) // NOPMD
            {
                mAxisValues[XboxButtonMap.RIGHT_X_AXIS] = sNEGATIVE_VALUE;
            }
            else
            {
                mAxisValues[XboxButtonMap.RIGHT_X_AXIS] = 0;
            }

            // Left Trigger
            if (mController.getComponent(Identifier.Key.C).getPollData() != 0) // NOPMD
            {
                mAxisValues[XboxButtonMap.LEFT_TRIGGER] = sPOSITIVE_VALUE;
            }
            else
            {
                mAxisValues[XboxButtonMap.LEFT_TRIGGER] = sNEGATIVE_VALUE;
            }

            // Right Trigger
            if (mController.getComponent(Identifier.Key.N).getPollData() != 0) // NOPMD
            {
                mAxisValues[XboxButtonMap.RIGHT_TRIGGER] = sPOSITIVE_VALUE;
            }
            else
            {
                mAxisValues[XboxButtonMap.RIGHT_TRIGGER] = sNEGATIVE_VALUE;
            }
        }

        return mAxisValues;
    }

    public short[] getPovValues()
    {
        boolean up = mController.getComponent(Identifier.Key.UP).getPollData() != 0;
        boolean right = mController.getComponent(Identifier.Key.RIGHT).getPollData() != 0;
        boolean down = mController.getComponent(Identifier.Key.DOWN).getPollData() != 0;
        boolean left = mController.getComponent(Identifier.Key.LEFT).getPollData() != 0;

        if (up && right)
        {
            mPovValues[0] = 45;
        }
        else if (right && down)
        {
            mPovValues[0] = 135;
        }
        else if (left && down)
        {
            mPovValues[0] = 225;
        }
        else if (left && up)
        {
            mPovValues[0] = -45;
        }
        else if (up)
        {
            mPovValues[0] = 0;
        }
        else if (right)
        {
            mPovValues[0] = 90;
        }
        else if (down)
        {
            mPovValues[0] = 180;
        }
        else if (left)
        {
            mPovValues[0] = 270;
        }
        else
        {
            mPovValues[0] = -1;
        }

        return mPovValues;
    }
}
