package edu.wpi.first.wpilibj.sim;

import java.io.Closeable;

import edu.wpi.first.hal.sim.mockdata.ADXL345_I2CAccelerometerDataJNI;
import edu.wpi.first.hal.sim.mockdata.ADXRS450_GyroDataJNI;

public class ADXRS450_GyroSim implements Closeable
{
	private final long nativePointer;

	public ADXRS450_GyroSim(int port)
	{
		nativePointer = ADXRS450_GyroDataJNI.createGyro(port);
	}

	public double getAngle()
	{
		return ADXRS450_GyroDataJNI.getAngle(nativePointer);
	}

	public void setAngle(double angle)
	{
		ADXRS450_GyroDataJNI.setAngle(nativePointer, angle);
	}

    @Override
    public void close()
    {
        ADXL345_I2CAccelerometerDataJNI.deleteAccelerometer(nativePointer);
    }
}
