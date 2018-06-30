/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.sim.NotifyCallback;

public class PCMDataJNI
{
    public static native void registerAllSolenoidCallbacks(int index, int channel, NotifyCallback callback, boolean initialNotify);

    public static native void resetData(int index);
}
