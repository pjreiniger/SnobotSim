/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.hal.sim.mockdata;

import edu.wpi.first.wpilibj.hal.XXJNIWrapper;

public class SimulatorJNI extends XXJNIWrapper {
  public static native void waitForProgramStart();
  public static native void setProgramStarted();
  public static native void restartTiming();
  public static native void resetHandles();

    public static native void initialize();
}
