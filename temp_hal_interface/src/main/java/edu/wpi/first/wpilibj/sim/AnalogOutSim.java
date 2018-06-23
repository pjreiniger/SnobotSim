/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.AnalogOutDataJNI;

public class AnalogOutSim {
  private final int m_index;

  public AnalogOutSim(int index) {
    m_index = index;
  }

  public double getVoltage() {
        return AnalogOutDataJNI.getVoltage(m_index);
  }
  public void setVoltage(double voltage) {
        AnalogOutDataJNI.setVoltage(m_index, voltage);
  }

}
