package com.snobot.simulator.module_wrapper.interfaces;

public interface IRelayWrapper extends ISensorWrapper
{

    boolean getRelayReverse();

    boolean getRelayForwards();

    void setRelayReverse(boolean aReverse);

    void setRelayForwards(boolean aForwards);

}
