/*
 * BaseAdxAccelWrapper.cpp
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#include "SnobotSim/SimulatorComponents/AdxWrappers/BaseAdxAccelWrapper.h"

#include "SnobotSim/SensorActuatorRegistry.h"

const std::string BaseAdxAccelWrapper::AccelerometerWrapper::TYPE = "baseAdxAccel";

BaseAdxAccelWrapper::BaseAdxAccelWrapper(const std::string& aBaseName, const std::string& aDeviceName, int aBasePort) :
        mXWrapper(new AccelerometerWrapper(LazySimDoubleWrapper{ aDeviceName, "X Accel" })),
        mYWrapper(new AccelerometerWrapper(LazySimDoubleWrapper{ aDeviceName, "Y Accel" })),
        mZWrapper(new AccelerometerWrapper(LazySimDoubleWrapper{ aDeviceName, "Z Accel" }))
{
    SensorActuatorRegistry::Get().Register(aBasePort + 0, mXWrapper);
    SensorActuatorRegistry::Get().Register(aBasePort + 1, mYWrapper);
    SensorActuatorRegistry::Get().Register(aBasePort + 2, mZWrapper);
}

BaseAdxAccelWrapper::~BaseAdxAccelWrapper()
{
}

BaseAdxAccelWrapper::AccelerometerWrapper::AccelerometerWrapper(const LazySimDoubleWrapper& aSimWrapper) :
        AModuleWrapper("Hello"),
        mSimWrapper(aSimWrapper)
{
}

void BaseAdxAccelWrapper::AccelerometerWrapper::SetAcceleration(double aAcceleration)
{
    mSimWrapper.set(aAcceleration);
}

double BaseAdxAccelWrapper::AccelerometerWrapper::GetAcceleration()
{
    return mSimWrapper.get();
}
