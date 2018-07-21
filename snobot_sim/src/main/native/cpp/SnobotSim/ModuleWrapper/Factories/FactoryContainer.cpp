/*
 * FactoryContainer.cpp
 *
 *  Created on: Jul 18, 2018
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/Factories/FactoryContainer.h"

FactoryContainer FactoryContainer::sINSTANCE;

FactoryContainer::FactoryContainer() :
        mAccelerometerFactory(new AccelerometerFactory),
        mAnalogInFactory(new AnalogInFactory),
        mAnalogOutFactory(new AnalogOutFactory),
        mDigitalIoFactory(new DigitalIoFactory),
        mEncoderFactory(new EncoderFactory),
        mGyroFactory(new GyroFactory),
        mI2CWrapperFactory(new I2CWrapperFactory),
        mRelayFactory(new RelayFactory),
        mSolenoidFactory(new SolenoidFactory),
        mSpeedControllerFactory(new SpeedControllerFactory),
        mSpiWrapperFactory(new SpiWrapperFactory)
{
}

FactoryContainer::~FactoryContainer()
{
}

FactoryContainer& FactoryContainer::Get()
{
    return sINSTANCE;
}

std::shared_ptr<AccelerometerFactory> FactoryContainer::GetAccelerometerFactory()
{
    return mAccelerometerFactory;
}

std::shared_ptr<AnalogInFactory> FactoryContainer::GetAnalogInFactory()
{
    return mAnalogInFactory;
}

std::shared_ptr<AnalogOutFactory> FactoryContainer::GetAnalogOutFactory()
{
    return mAnalogOutFactory;
}

std::shared_ptr<DigitalIoFactory> FactoryContainer::GetDigitalIoFactory()
{
    return mDigitalIoFactory;
}

std::shared_ptr<EncoderFactory> FactoryContainer::GetEncoderFactory()
{
    return mEncoderFactory;
}

std::shared_ptr<GyroFactory> FactoryContainer::GetGyroFactory()
{
    return mGyroFactory;
}

std::shared_ptr<I2CWrapperFactory> FactoryContainer::GetI2CWrapperFactory()
{
    return mI2CWrapperFactory;
}

std::shared_ptr<RelayFactory> FactoryContainer::GetRelayFactory()
{
    return mRelayFactory;
}

std::shared_ptr<SolenoidFactory> FactoryContainer::GetSolenoidFactory()
{
    return mSolenoidFactory;
}

std::shared_ptr<SpeedControllerFactory> FactoryContainer::GetSpeedControllerFactory()
{
    return mSpeedControllerFactory;
}

std::shared_ptr<SpiWrapperFactory> FactoryContainer::GetSpiWrapperFactory()
{
    return mSpiWrapperFactory;
}
