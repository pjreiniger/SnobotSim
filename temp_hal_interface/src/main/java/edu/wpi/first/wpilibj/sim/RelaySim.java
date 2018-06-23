/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.RelayDataJNI;

public class RelaySim {
  private final int m_index;

  public RelaySim(int index) {
    m_index = index;
  }

  public boolean getForward() {
    return RelayDataJNI.getForward(m_index);
  }
  public void setForward(boolean forward) {
    RelayDataJNI.setForward(m_index, forward);
  }

  public boolean getReverse() {
    return RelayDataJNI.getReverse(m_index);
  }
  public void setReverse(boolean reverse) {
    RelayDataJNI.setReverse(m_index, reverse);
  }

    public void resetData()
    {
        RelayDataJNI.resetData(m_index);
    }
}
