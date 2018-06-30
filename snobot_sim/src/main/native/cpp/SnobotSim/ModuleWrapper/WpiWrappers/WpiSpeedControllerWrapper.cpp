/*
 * WpiSpeedControllerWrapper.cpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiSpeedControllerWrapper.h"

#include "SnobotSim/PortUnwrapper.h"

WpiSpeedControllerWrapper::WpiSpeedControllerWrapper(int aPort) :
        AModuleWrapper("Speed Controller " + std::to_string(UnwrapPort(aPort))),
        mId(aPort),
        mMotorSimulator(new NullMotorSimulator),
        mFeedbackSensor(new NullFeedbackSensor)
{
}

WpiSpeedControllerWrapper::~WpiSpeedControllerWrapper()
{
}

int WpiSpeedControllerWrapper::GetId()
{
    return mId;
}

void WpiSpeedControllerWrapper::SetMotorSimulator(
        const std::shared_ptr<IMotorSimulator>& aSimulator)
{
    mMotorSimulator = aSimulator;
}

const std::shared_ptr<IMotorSimulator>& WpiSpeedControllerWrapper::GetMotorSimulator()
{
    return mMotorSimulator;
}

void WpiSpeedControllerWrapper::SetFeedbackSensor(const std::shared_ptr<IFeedbackSensor>& aFeedbackSensor)
{
    mFeedbackSensor = aFeedbackSensor;
}

const std::shared_ptr<IFeedbackSensor>& WpiSpeedControllerWrapper::GetFeedbackSensor()
{
    return mFeedbackSensor;
}

double WpiSpeedControllerWrapper::GetVoltagePercentage()
{
    return mMotorSimulator->GetVoltagePercentage();
}

void WpiSpeedControllerWrapper::SetVoltagePercentage(double aSpeed)
{
    mMotorSimulator->SetVoltagePercentage(aSpeed);
}

void WpiSpeedControllerWrapper::Update(double aWaitTime)
{
    mMotorSimulator->Update(aWaitTime);
    mFeedbackSensor->SetPosition(GetPosition());
    mFeedbackSensor->SetVelocity(GetVelocity());
}

double WpiSpeedControllerWrapper::GetPosition()
{
    return mMotorSimulator->GetPosition();
}

double WpiSpeedControllerWrapper::GetVelocity()
{
    return mMotorSimulator->GetVelocity();
}

double WpiSpeedControllerWrapper::GetCurrent()
{
    return mMotorSimulator->GetCurrent();
}

double WpiSpeedControllerWrapper::GetAcceleration()
{
    return mMotorSimulator->GetAcceleration();
}

void WpiSpeedControllerWrapper::Reset()
{
    mMotorSimulator->Reset();
}

void WpiSpeedControllerWrapper::Reset(double aPosition, double aVelocity,
        double aCurrent)
{
    mMotorSimulator->Reset(aPosition, aVelocity, aCurrent);
}
