package com.snobot.simulator.navx;

public class SpiNavxSimulator implements INavxSimulator
{
    private final long mNativePointer;

    public SpiNavxSimulator(int aPort)
    {
        mNativePointer = SpiNavxSimulatorJni.createNavx(aPort);
    }

    @Override
    public double getXAccel()
    {
        return SpiNavxSimulatorJni.getXAccel(mNativePointer);
    }

    @Override
    public double getYAccel()
    {
        return SpiNavxSimulatorJni.getYAccel(mNativePointer);
    }

    @Override
    public double getZAccel()
    {
        return SpiNavxSimulatorJni.getZAccel(mNativePointer);
    }

    @Override
    public void setXAccel(double aX)
    {
        SpiNavxSimulatorJni.setXAccel(mNativePointer, aX);
    }

    @Override
    public void setYAccel(double aY)
    {
        SpiNavxSimulatorJni.setYAccel(mNativePointer, aY);
    }

    @Override
    public void setZAccel(double aZ)
    {
        SpiNavxSimulatorJni.setZAccel(mNativePointer, aZ);
    }

    @Override
    public double getYaw()
    {
        return SpiNavxSimulatorJni.getYaw(mNativePointer);
    }

    @Override
    public double getPitch()
    {
        return SpiNavxSimulatorJni.getPitch(mNativePointer);
    }

    @Override
    public double getRoll()
    {
        return SpiNavxSimulatorJni.getRoll(mNativePointer);
    }

    @Override
    public void setYaw(double aYaw)
    {
        SpiNavxSimulatorJni.setYaw(mNativePointer, aYaw);
    }

    @Override
    public void setPitch(double aPitch)
    {
        SpiNavxSimulatorJni.setPitch(mNativePointer, aPitch);
    }

    @Override
    public void setRoll(double aRoll)
    {
        SpiNavxSimulatorJni.setRoll(mNativePointer, aRoll);
    }
}
