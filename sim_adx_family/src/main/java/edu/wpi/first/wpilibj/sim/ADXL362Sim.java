package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.ADXL345_I2CAccelerometerDataJNI;
import edu.wpi.first.hal.sim.mockdata.ADXL362AccelerometerDataJNI;

public class ADXL362Sim implements IThreeAxisAccelerometer
{
	private final long nativePointer;

	public ADXL362Sim(int port)
	{
		nativePointer = ADXL362AccelerometerDataJNI.createAccelerometer(port);
	}

	@Override
	public double getX()
	{
		return ADXL362AccelerometerDataJNI.getX(nativePointer);
	}

	@Override
	public double getY()
	{
		return ADXL362AccelerometerDataJNI.getY(nativePointer);
	}

	@Override
	public double getZ()
	{
		return ADXL362AccelerometerDataJNI.getZ(nativePointer);
	}

	@Override
	public void setX(double x)
	{
		ADXL362AccelerometerDataJNI.setX(nativePointer, x);
	}

	@Override
	public void setY(double y)
	{
		ADXL362AccelerometerDataJNI.setY(nativePointer, y);
	}

	@Override
	public void setZ(double z)
	{
		ADXL362AccelerometerDataJNI.setZ(nativePointer, z);
	}

    @Override
    public void close()
    {
        ADXL345_I2CAccelerometerDataJNI.deleteAccelerometer(nativePointer);
    }
}
