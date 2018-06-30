/*
 * BaseAdxAccelWrapper.cpp
 *
 *  Created on: Nov 23, 2017
 *      Author: PJ
 */

#include "SnobotSim/SimulatorComponents/AdxWrappers/BaseAdxAccelWrapper.h"

#include "SnobotSim/SensorActuatorRegistry.h"

BaseAdxAccelWrapper::BaseAdxAccelWrapper(int aBasePort, const std::shared_ptr<hal::ThreeAxisAccelerometerData>& aAccel) :
        mXWrapper(new AccelerometerWrapper(AccelerometerWrapper::AXIS_X, aAccel)),
        mYWrapper(new AccelerometerWrapper(AccelerometerWrapper::AXIS_Y, aAccel)),
        mZWrapper(new AccelerometerWrapper(AccelerometerWrapper::AXIS_Z, aAccel))
{
    SensorActuatorRegistry::Get().Register(aBasePort + 0, mXWrapper);
    SensorActuatorRegistry::Get().Register(aBasePort + 1, mYWrapper);
    SensorActuatorRegistry::Get().Register(aBasePort + 2, mZWrapper);
}

BaseAdxAccelWrapper::~BaseAdxAccelWrapper()
{
}

BaseAdxAccelWrapper::AccelerometerWrapper::AccelerometerWrapper(AxisType aAxisType, const std::shared_ptr<hal::ThreeAxisAccelerometerData>& aAccel) :
        IAccelerometerWrapper("Hello"),
        mAxisType(aAxisType),
        mAccel(aAccel)
{
}

void BaseAdxAccelWrapper::AccelerometerWrapper::SetAcceleration(double aAcceleration)
{
    switch (mAxisType)
    {
    case AXIS_X:
        mAccel->SetX(aAcceleration);
        break;
    case AXIS_Y:
        mAccel->SetY(aAcceleration);
        break;
    case AXIS_Z:
        mAccel->SetZ(aAcceleration);
        break;
    }
}

double BaseAdxAccelWrapper::AccelerometerWrapper::GetAcceleration()
{
    switch (mAxisType)
    {
    case AXIS_X:
        return mAccel->GetX();
    case AXIS_Y:
        return mAccel->GetY();
    case AXIS_Z:
        return mAccel->GetZ();
    }
    return 0;
}
