package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.ADXRS450_GyroDataJNI;

public class ADXRS450_GyroSim {
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
}
