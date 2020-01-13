package com.snobot.simulator.joysticks;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class SnobotSimJoystickInterface implements IJoystickInterface
{
    @Override
    public void sendJoystickUpdate()
    {
        IMockJoystick[] joysticks = JoystickFactory.getInstance().getAll();
        for (int i = 0; i < joysticks.length; ++i)
        {
            IMockJoystick joystick = joysticks[i];
            DataAccessorFactory.getInstance().getDriverStationAccessor().setJoystickInformation(i, joystick.getAxisValues(), joystick.getPovValues(),
                    joystick.getButtonCount(), joystick.getButtonMask());
        }
    }

    @Override
    public void waitForLoop()
    {
        DataAccessorFactory.getInstance().getDriverStationAccessor().waitForNextUpdateLoop();
    }

}
