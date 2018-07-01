package edu.wpi.first.wpilibj.sim;

import java.io.Closeable;

public interface IThreeAxisAccelerometer extends Closeable
{

	double getX();

	double getY();

	double getZ();

	void setX(double x);

	void setY(double y);

	void setZ(double z);

}
