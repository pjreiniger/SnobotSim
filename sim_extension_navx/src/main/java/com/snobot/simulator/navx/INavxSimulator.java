package com.snobot.simulator.navx;

public interface INavxSimulator
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
