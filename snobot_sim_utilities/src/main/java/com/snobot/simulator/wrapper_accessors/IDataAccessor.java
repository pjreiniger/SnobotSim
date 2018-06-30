package com.snobot.simulator.wrapper_accessors;

public interface IDataAccessor
{
    String getAccessorType();

    AccelerometerWrapperAccessor getAccelerometerAccessor();

    GyroWrapperAccessor getGyroAccessor();

    AnalogSourceWrapperAccessor getAnalogInAccessor();

    AnalogSourceWrapperAccessor getAnalogOutAccessor();

    DigitalSourceWrapperAccessor getDigitalAccessor();

    EncoderWrapperAccessor getEncoderAccessor();

    RelayWrapperAccessor getRelayAccessor();

    SolenoidWrapperAccessor getSolenoidAccessor();

    SpeedControllerWrapperAccessor getSpeedControllerAccessor();

    SimulatorDataAccessor getSimulatorDataAccessor();

    String getInitializationErrors();
}
