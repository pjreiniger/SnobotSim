package com.snobot.simulator.joysticks.joystick_specializations;

import com.snobot.simulator.joysticks.IMockJoystick;

import net.java.games.input.Controller;

public class NullJoystick implements IMockJoystick
{
    public static final String sNAME = "Null Joystick";

    protected static final int sNUM_BUTTONS = 10;
    protected static final int sNUM_AXIS = 6;
    protected static final int sNUM_POV = 1;

    protected float[] mAxis;
    protected short[] mPov;
    protected int mButtonMask;

    public NullJoystick()
    {
        mButtonMask = 0;
        mAxis = new float[sNUM_AXIS];
        mPov = new short[sNUM_POV];
        for (int i = 0; i < mPov.length; ++i)
        {
            mPov[i] = -1;
        }
    }

    @Override
    public int getAxisCount()
    {
        return sNUM_AXIS;
    }

    @Override
    public int getButtonCount()
    {
        return sNUM_BUTTONS;
    }

    @Override
    public void setRumble(short aRumble)
    {
        // Nothing to do
    }

    @Override
    public float[] getAxisValues()
    {
        return mAxis;
    }

    @Override
    public short[] getPovValues()
    {
        return mPov;
    }

    @Override
    public int getButtonMask()
    {
        return mButtonMask;
    }

    @Override
    public boolean getRawButton(int aIndex)
    {
        return (getButtonMask() & (1 << aIndex)) != 0;
    }

    @Override
    public double getRawAxis(int aIndex)
    {
        return 0;
    }

    @Override
    public String getName()
    {
        return sNAME;
    }

    @Override
    public Controller getController()
    {
        return null;
    }

}
