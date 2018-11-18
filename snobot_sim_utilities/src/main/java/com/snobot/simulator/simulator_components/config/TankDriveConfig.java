package com.snobot.simulator.simulator_components.config;

public class TankDriveConfig
{
    private int mLeftMotorHandle;
    private int mRightMotorHandle;
    private int mGyroHandle;
    private double mTurnKp;

    public TankDriveConfig()
    {
        this(-1, -1, -1, 1);
    }

    public TankDriveConfig(int aLeftHandle, int aRightHandle, int aGyroHandle, double aTurnKp)
    {
        mLeftMotorHandle = aLeftHandle;
        mRightMotorHandle = aRightHandle;
        mGyroHandle = aGyroHandle;
        mTurnKp = aTurnKp;
    }

    public int getmLeftMotorHandle()
    {
        return mLeftMotorHandle;
    }

    public void setmLeftMotorHandle(int aLeftMotorHandle)
    {
        this.mLeftMotorHandle = aLeftMotorHandle;
    }

    public int getmRightMotorHandle()
    {
        return mRightMotorHandle;
    }

    public void setmRightMotorHandle(int aRightMotorHandle)
    {
        this.mRightMotorHandle = aRightMotorHandle;
    }

    public int getmGyroHandle()
    {
        return mGyroHandle;
    }

    public void setmGyroHandle(int aGyroHandle)
    {
        this.mGyroHandle = aGyroHandle;
    }

    public double getmTurnKp()
    {
        return mTurnKp;
    }

    public void setmTurnKp(double aTurnKp)
    {
        this.mTurnKp = aTurnKp;
    }

}
