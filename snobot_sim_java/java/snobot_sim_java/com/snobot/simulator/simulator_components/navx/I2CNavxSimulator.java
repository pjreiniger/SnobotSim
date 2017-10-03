package com.snobot.simulator.simulator_components.navx;

import java.nio.ByteBuffer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.snobot.simulator.jni.SensorFeedbackJni;
import com.snobot.simulator.simulator_components.II2CWrapper;

public class I2CNavxSimulator extends NavxSimulator implements II2CWrapper
{
    private static final Logger sLOGGER = Logger.getLogger(I2CNavxSimulator.class);
    public static final int SPI_NAVX_OFFSET = 250;

    public I2CNavxSimulator(int aPort)
    {
        super(aPort, SPI_NAVX_OFFSET);
    }

    @Override
    public void handleRead()
    {
        ByteBuffer lastWriteValue = ByteBuffer.allocateDirect(4);
        SensorFeedbackJni.getI2CLastWrite(mNativePort, lastWriteValue, 4);
        lastWriteValue.rewind();
        int lastWrittenAddress = lastWriteValue.get();

        ByteBuffer toSend = null;

        if (lastWrittenAddress == 0x00)
        {
            toSend = createConfigBuffer();
        }
        else if (lastWrittenAddress == 0x04)
        {
            toSend = createDataBuffer(lastWrittenAddress);
        }
        else
        {
            sLOGGER.log(Level.ERROR, "Unknown last write address " + lastWrittenAddress);
        }

        if (toSend != null)
        {
            SensorFeedbackJni.setI2CValueForRead(mNativePort, toSend, toSend.capacity());
        }
    }

}