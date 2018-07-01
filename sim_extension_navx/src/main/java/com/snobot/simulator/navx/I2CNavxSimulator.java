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
    public void setXAccel(double aX)
    {
        I2CNavxSimulatorJni.setXAccel(mNativePointer, aX);
    }

    @Override
    public void setYAccel(double aY)
    {
        I2CNavxSimulatorJni.setYAccel(mNativePointer, aY);
    }

    @Override
    public void setZAccel(double aZ)
    {
        I2CNavxSimulatorJni.setZAccel(mNativePointer, aZ);
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
    public void setYaw(double aYaw)
    {
        I2CNavxSimulatorJni.setYaw(mNativePointer, aYaw);
    }

    @Override
    public void setPitch(double aPitch)
    {
        I2CNavxSimulatorJni.setPitch(mNativePointer, aPitch);
    }

    @Override
    public void setRoll(double aRoll)
    {
        I2CNavxSimulatorJni.setRoll(mNativePointer, aRoll);
    }

    @Override
    public void close()
    {
        I2CNavxSimulatorJni.deleteNavx(mNativePointer);
    }
}
