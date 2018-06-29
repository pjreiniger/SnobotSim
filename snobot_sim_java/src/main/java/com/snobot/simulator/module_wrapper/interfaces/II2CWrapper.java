package com.snobot.simulator.module_wrapper.interfaces;

import java.nio.ByteBuffer;

public interface II2CWrapper extends ISensorWrapper
{
    void handleRead(ByteBuffer aBuffer);

    void handleWrite(ByteBuffer aBuffer);

}
