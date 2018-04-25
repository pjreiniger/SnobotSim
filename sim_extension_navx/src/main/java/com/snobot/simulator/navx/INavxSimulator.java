package com.snobot.simulator.navx;

public interface INavxSimulator
{

    double getXAccel();

    double getYAccel();

    double getZAccel();

    void setXAccel(double x);

    void setYAccel(double y);

    void setZAccel(double z);

    double getYaw();

    double getPitch();

    double getRoll();

    void setYaw(double yaw);

    void setPitch(double pitch);

    void setRoll(double roll);

}