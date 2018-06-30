package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.ADXL345_SPIAccelerometerDataJNI;

public class ADXL345_SpiSim implements IThreeAxisAccelerometer {
	private final long nativePointer;

	public ADXL345_SpiSim(int port)
	{
		nativePointer = ADXL345_SPIAccelerometerDataJNI.createAccelerometer(port);
	}

	@Override
	public double getX()
	{
		return ADXL345_SPIAccelerometerDataJNI.getX(nativePointer);
	}

	@Override
	public double getY()
	{
		return ADXL345_SPIAccelerometerDataJNI.getY(nativePointer);
	}

	@Override
	public double getZ()
	{
		return ADXL345_SPIAccelerometerDataJNI.getZ(nativePointer);
	}

	@Override
	public void setX(double x)
	{
		ADXL345_SPIAccelerometerDataJNI.setX(nativePointer, x);
	}

	@Override
	public void setY(double y)
	{
		ADXL345_SPIAccelerometerDataJNI.setY(nativePointer, y);
	}

	@Override
	public void setZ(double z)
	{
		ADXL345_SPIAccelerometerDataJNI.setZ(nativePointer, z);
	}
}
