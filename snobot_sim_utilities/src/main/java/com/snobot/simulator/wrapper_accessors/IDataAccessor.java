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

    SpiWrapperAccessor getSpiAccessor();

    I2CWrapperAccessor getI2CAccessor();

    SpeedControllerWrapperAccessor getSpeedControllerAccessor();

    DriverStationDataAccessor getDriverStationAccessor();

    SimulatorDataAccessor getSimulatorDataAccessor();

    String getInitializationErrors();
}
