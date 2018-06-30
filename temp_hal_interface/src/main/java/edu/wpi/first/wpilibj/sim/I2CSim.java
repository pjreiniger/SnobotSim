/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.I2CDataJNI;

public class I2CSim {
  private final int m_index;

  public I2CSim(int index) {
    m_index = index;
  }

    public void registerReadCallback(BufferCallback callback)
    {
        I2CDataJNI.registerReadCallback(m_index, callback);
  }

    public void registerWriteCallback(ConstBufferCallback callback)
    {
        I2CDataJNI.registerWriteCallback(m_index, callback);
  }

  public void resetData() {
    I2CDataJNI.resetData(m_index);
  }
}
