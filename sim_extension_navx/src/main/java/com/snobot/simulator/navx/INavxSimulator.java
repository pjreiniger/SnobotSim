package com.snobot.simulator.navx;

import java.io.Closeable;

public interface INavxSimulator extends Closeable
{

    double getXAccel();

    double getYAccel();

    double getZAccel();

    void setXAccel(double aX);

    void setYAccel(double aY);

    void setZAccel(double aZ);

    double getYaw();

    double getPitch();

    double getRoll();

    void setYaw(double aYaw);

    void setPitch(double aPitch);

    void setRoll(double aRoll);

}
