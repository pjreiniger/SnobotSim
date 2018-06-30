package com.snobot.simulator.config;

public class EncoderConfig extends BasicModuleConfig
{
    private int mConnectedSpeedControllerHandle;

    public EncoderConfig()
    {
        this(-1, "Unset", null, -1);
    }

    public EncoderConfig(int aHandle, String aName, String aType, int aConnectedSpeedControllerHandle)
    {
        super(aHandle, aName, aType);
        mConnectedSpeedControllerHandle = aConnectedSpeedControllerHandle;
    }

    public int getmConnectedSpeedControllerHandle()
    {
        return mConnectedSpeedControllerHandle;
    }

    public void setmConnectedSpeedControllerHandle(int aConnectedSpeedControllerHandle)
    {
        this.mConnectedSpeedControllerHandle = aConnectedSpeedControllerHandle;
    }

}
