package com.snobot.test.utilities;

import com.snobot.simulator.joysticks.IMockJoystick;

import net.java.games.input.Controller;

public class MockIJoystick implements IMockJoystick
{
    public float[] mAxisValues;
    public short[] mPovValues;
    public boolean[] mButtonValues;

    public MockIJoystick()
    {
        mAxisValues = new float[10];
        mPovValues = new short[10];
        mButtonValues = new boolean[20];
    }

    @Override
    public int getAxisCount()
    {
        return mAxisValues.length;
    }

    @Override
    public int getButtonCount()
    {
        return mButtonValues.length;
    }

    @Override
    public void setRumble(short aRumble)
    {
        // Nothing to do
    }

    @Override
    public float[] getAxisValues()
    {
        return mAxisValues;
    }

    @Override
    public short[] getPovValues()
    {
        return mPovValues;
    }

    @Override
    public int getButtonMask()
    {
        return 0;
    }

    @Override
    public boolean getRawButton(int aIndex)
    {
        return mButtonValues[aIndex];
    }

    @Override
    public double getRawAxis(int aIndex)
    {
        return mAxisValues[aIndex];
    }

    @Override
    public String getName()
    {
        return "TestJoystick";
    }

    @Override
    public Controller getController()
    {
        return null;
    }

}
