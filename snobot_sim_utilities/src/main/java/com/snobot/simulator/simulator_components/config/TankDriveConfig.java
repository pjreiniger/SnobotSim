package com.snobot.simulator.simulator_components.config;

public class TankDriveConfig
{
    private int mLeftEncoderHandle;
    private int mRightEncoderHandle;
    private int mGyroHandle;
    private double mTurnKp;

    public TankDriveConfig()
    {
        this(-1, -1, -1, 1);
    }

    public TankDriveConfig(int aLeftHandle, int aRightHandle, int aGyroHandle, double aTurnKp)
    {
        mLeftEncoderHandle = aLeftHandle;
        mRightEncoderHandle = aRightHandle;
        mGyroHandle = aGyroHandle;
        mTurnKp = aTurnKp;
    }

    public int getmLeftEncoderHandle()
    {
        return mLeftEncoderHandle;
    }

    public void setmLeftEncoderHandle(int aLeftEncoderHandle)
    {
        this.mLeftEncoderHandle = aLeftEncoderHandle;
    }

    public int getmRightEncoderHandle()
    {
        return mRightEncoderHandle;
    }

    public void setmRightEncoderHandle(int aRightEncoderHandle)
    {
        this.mRightEncoderHandle = aRightEncoderHandle;
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
