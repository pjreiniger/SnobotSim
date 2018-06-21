package com.snobot.simulator.module_wrapper.interfaces;

import java.nio.ByteBuffer;

public interface ISpiWrapper
{
    void handleRead(ByteBuffer aBuffer);

    void handleWrite(ByteBuffer aBuffer);
}
