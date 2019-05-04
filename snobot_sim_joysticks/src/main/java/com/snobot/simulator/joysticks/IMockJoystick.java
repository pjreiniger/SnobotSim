package com.snobot.simulator.joysticks;

import net.java.games.input.Controller;

public interface IMockJoystick
{

    int getAxisCount();

    int getButtonCount();

    void setRumble(short aRumble);

    float[] getAxisValues();

    short[] getPovValues();

    int getButtonMask();

    boolean getRawButton(int aIndex);

    double getRawAxis(int aIndex);

    String getName();

    Controller getController();

}
