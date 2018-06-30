/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.DIODataJNI;

public class DIOSim {
  private final int m_index;

  public DIOSim(int index) {
    m_index = index;
  }

  public boolean getValue() {
    return DIODataJNI.getValue(m_index);
  }
  public void setValue(boolean value) {
    DIODataJNI.setValue(m_index, value);
  }
}
