/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.sim;

import edu.wpi.first.hal.sim.mockdata.SPIDataJNI;

public class SPISim {
    private final int m_index;

    public SPISim(int index)
    {
        m_index = index;
    }

    public void registerReadCallback(BufferCallback callback)
    {
        SPIDataJNI.registerReadCallback(m_index, callback);
    }

    public void registerWriteCallback(ConstBufferCallback callback)
    {
        SPIDataJNI.registerWriteCallback(m_index, callback);
    }

    public void resetData()
    {
        SPIDataJNI.resetData(m_index);
    }
}
