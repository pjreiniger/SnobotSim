package com.snobot.simulator.navx;

public class SpiNavxSimulator implements INavxSimulator
{
    private final long mNativePointer;

    public SpiNavxSimulator(int aPort)
    {
        mNativePointer = SpiNavxSimulatorJni.createNavx(aPort);
        System.out.println("Creating SPI : " + mNativePointer);
    }

    @Override
    protected void finalize()
    {
        System.out.println("Cleaning up");
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
    public void setXAccel(double x)
    {
        SpiNavxSimulatorJni.setXAccel(mNativePointer, x);
    }

    @Override
    public void setYAccel(double y)
    {
        SpiNavxSimulatorJni.setYAccel(mNativePointer, y);
    }

    @Override
    public void setZAccel(double z)
    {
        SpiNavxSimulatorJni.setZAccel(mNativePointer, z);
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
    public void setYaw(double yaw)
    {
        SpiNavxSimulatorJni.setYaw(mNativePointer, yaw);
    }

    @Override
    public void setPitch(double pitch)
    {
        SpiNavxSimulatorJni.setPitch(mNativePointer, pitch);
    }

    @Override
    public void setRoll(double roll)
    {
        SpiNavxSimulatorJni.setRoll(mNativePointer, roll);
    }
}
