package com.snobot.simulator.config;

/**
 * Base class for config objects. Holds the ID handle and the name of the
 * configurable module
 *
 * @author PJ
 *
 */
public class BasicModuleConfig
{
    public int mHandle;
    public String mName;
    public String mType;

    public BasicModuleConfig()
    {
        this(-1, "Unset", null);
    }

    public BasicModuleConfig(int aHandle, String aName, String aType)
    {
        mHandle = aHandle;
        mName = aName;
        mType = aType;
    }

    public int getmHandle()
    {
        return mHandle;
    }

    public void setmHandle(int aHandle)
    {
        this.mHandle = aHandle;
    }

    public String getmName()
    {
        return mName;
    }

    public void setmName(String aName)
    {
        this.mName = aName;
    }

    public String getmType()
    {
        return mType;
    }

    public void setmType(String aType)
    {
        this.mType = aType;
    }

}
