package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.ADXL345_I2CAccelerometerDataJNI;

public class ADXL345_I2CSim implements IThreeAxisAccelerometer {
	private final long nativePointer;

	public ADXL345_I2CSim(int port)
	{
		nativePointer = ADXL345_I2CAccelerometerDataJNI.createAccelerometer(port);
	}

	@Override
	public double getX()
	{
		return ADXL345_I2CAccelerometerDataJNI.getX(nativePointer);
	}

	@Override
	public double getY()
	{
		return ADXL345_I2CAccelerometerDataJNI.getY(nativePointer);
	}

	@Override
	public double getZ()
	{
		return ADXL345_I2CAccelerometerDataJNI.getZ(nativePointer);
	}

	@Override
	public void setX(double x)
	{
		ADXL345_I2CAccelerometerDataJNI.setX(nativePointer, x);
	}

	@Override
	public void setY(double y)
	{
		ADXL345_I2CAccelerometerDataJNI.setY(nativePointer, y);
	}

	@Override
	public void setZ(double z)
	{
		ADXL345_I2CAccelerometerDataJNI.setZ(nativePointer, z);
	}
}
