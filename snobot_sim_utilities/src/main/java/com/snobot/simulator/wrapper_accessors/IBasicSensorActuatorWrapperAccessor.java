package com.snobot.simulator.wrapper_accessors;

import java.util.List;

public interface IBasicSensorActuatorWrapperAccessor
{
    boolean isInitialized(int aPort);

    void setName(int aPort, String aName);

    String getName(int aPort);

    String getType(int aPort);

    boolean getWantsHidden(int aPort);

    public List<Integer> getPortList();

    boolean createSimulator(int aPort, String aType);

    void removeSimulator(int aPort);
}
