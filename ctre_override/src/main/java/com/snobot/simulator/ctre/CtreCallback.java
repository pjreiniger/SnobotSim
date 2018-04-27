package com.snobot.simulator.ctre;

import java.nio.ByteBuffer;

public interface CtreCallback
{
    void callback(String aName, int aDeviceId, ByteBuffer aBuffer, int aCount);
}
