package com.snobot.simulator.joysticks;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;

public class BaseJoystick implements IMockJoystick
{
    private static final Logger sLOGGER = LogManager.getLogger(BaseJoystick.class);

    protected final String mName;
    protected final List<Identifier> mAxis;
    protected final List<Identifier> mButtons;
    protected final List<Identifier> mPOV;
    protected final Controller mController;

    protected float[] mAxisValues;
    protected short[] mPovValues;

    public BaseJoystick(String aName, Controller aController, List<Identifier> aAxisList, List<Identifier> aButtonList, List<Identifier> aPOV)
    {
        mController = aController;
        mAxis = aAxisList;
        mButtons = aButtonList;
        mPOV = aPOV;
        mName = aName;

        mAxisValues = new float[mAxis.size()];
        mPovValues = new short[mPOV.size()];
    }

    public Controller getController()
    {
        return mController;
    }

    @Override
    public int getAxisCount()
    {
        return mAxis.size();
    }

    @Override
    public int getButtonCount()
    {
        return mButtons.size();
    }

    @Override
    public void setRumble(short aRumble)
    {
        // TODO implement rumble...
    }

    @Override
    public float[] getAxisValues()
    {

        if (mController == null)
        {
            sLOGGER.log(Level.WARN, "Controller is null.  The simulator could not setup a controller of type [" + mName + "]");
        }
        else
        {
            mController.poll();

            for (int i = 0; i < mAxis.size(); ++i)
            {
                Component c = mController.getComponent(mAxis.get(i));
                if (c != null)
                {
                    mAxisValues[i] = c.getPollData();
                }
            }
        }

        return mAxisValues;
    }

    @Override
    public int getButtonMask()
    {

        int output = 0;

        if (mController == null)
        {
            sLOGGER.log(Level.WARN, "Controller is null.  The simulator could not setup a controller of type [" + mName + "]");
        }
        else
        {
            mController.poll();

            for (int i = 0; i < mButtons.size(); ++i)
            {
                Component component = mController.getComponent(mButtons.get(i));
                if (component != null)
                {
                    int pressed = component.getPollData() == 0 ? 0 : 1;
                    output += pressed << i;
                }
            }
        }

        return output;
    }

    @Override
    public short[] getPovValues()
    {
        int i;
        for (i = 0; i < mPOV.size(); ++i)
        {
            Identifier id = mPOV.get(i);
            Component component = mController.getComponent(id);
            if (component != null)
            {
                double value = component.getPollData();
                if (value == 0)
                {
                    mPovValues[i] = -1;
                }
                else
                {
                    mPovValues[i] = (short) ((value - .25) * 360);
                }
            }
        }

        for (; i < mPovValues.length; ++i)
        {
            mPovValues[i] = -1;
        }

        return mPovValues;
    }

    public String getName()
    {
        return mName;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    public boolean getRawButton(int aIndex)
    {
        int mask = getButtonMask();
        return (mask & (1 << aIndex)) != 0;
    }

    @Override
    public double getRawAxis(int aIndex)
    {
        return mAxisValues[aIndex];
    }
}
