package com.snobot.simulator.navx;

public class I2CNavxSimulator implements INavxSimulator
{
    private final long mNativePointer;

    public I2CNavxSimulator(int aPort)
    {
        mNativePointer = I2CNavxSimulatorJni.createNavx(aPort);
    }

    @Override
    public double getXAccel()
    {
        return I2CNavxSimulatorJni.getXAccel(mNativePointer);
    }

    @Override
    public double getYAccel()
    {
        return I2CNavxSimulatorJni.getYAccel(mNativePointer);
    }

    @Override
    public double getZAccel()
    {
        return I2CNavxSimulatorJni.getZAccel(mNativePointer);
    }

    @Override
    public void setXAccel(double x)
    {
        I2CNavxSimulatorJni.setXAccel(mNativePointer, x);
    }

    @Override
    public void setYAccel(double y)
    {
        I2CNavxSimulatorJni.setYAccel(mNativePointer, y);
    }

    @Override
    public void setZAccel(double z)
    {
        I2CNavxSimulatorJni.setZAccel(mNativePointer, z);
    }

    @Override
    public double getYaw()
    {
        return I2CNavxSimulatorJni.getYaw(mNativePointer);
    }

    @Override
    public double getPitch()
    {
        return I2CNavxSimulatorJni.getPitch(mNativePointer);
    }

    @Override
    public double getRoll()
    {
        return I2CNavxSimulatorJni.getRoll(mNativePointer);
    }

    @Override
    public void setYaw(double yaw)
    {
        I2CNavxSimulatorJni.setYaw(mNativePointer, yaw);
    }

    @Override
    public void setPitch(double pitch)
    {
        I2CNavxSimulatorJni.setPitch(mNativePointer, pitch);
    }

    @Override
    public void setRoll(double roll)
    {
        I2CNavxSimulatorJni.setRoll(mNativePointer, roll);
    }
}
