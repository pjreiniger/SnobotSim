/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.EncoderDataJNI;

public class EncoderSim {
  private final int m_index;

  public EncoderSim(int index) {
    m_index = index;
  }

  public int getCount() {
    return EncoderDataJNI.getCount(m_index);
  }
  public void setCount(int count) {
    EncoderDataJNI.setCount(m_index, count);
  }

  public double getPeriod() {
    return EncoderDataJNI.getPeriod(m_index);
  }
  public void setPeriod(double period) {
    EncoderDataJNI.setPeriod(m_index, period);
  }

    public double getDistancePerPulse()
    {
        return EncoderDataJNI.getDistancePerPulse(m_index);
    }

    public void setDistancePerPulse(double distancePerPulse)
    {
        EncoderDataJNI.setDistancePerPulse(m_index, distancePerPulse);
    }

    public boolean getReset()
    {
        return EncoderDataJNI.getReset(m_index);
    }

    public void setReset(boolean reset)
    {
        EncoderDataJNI.setReset(m_index, reset);
    }
}
