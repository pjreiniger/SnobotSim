
#include "SnobotSim/ModuleWrapper/BaseSpeedControllerWrapper.h"

BaseSpeedControllerWrapper::BaseSpeedControllerWrapper(const std::string& aName, int aPort) :
        AModuleWrapper(aName),
        mId(aPort),
        mMotorSimulator(new NullMotorSimulator),
        mFeedbackSensor(new NullFeedbackSensor)
{
}

BaseSpeedControllerWrapper::~BaseSpeedControllerWrapper()
{
}

int BaseSpeedControllerWrapper::GetId()
{
    return mId;
}

void BaseSpeedControllerWrapper::SetMotorSimulator(
        const std::shared_ptr<IMotorSimulator>& aSimulator)
{
    mMotorSimulator = aSimulator;
}

const std::shared_ptr<IMotorSimulator>& BaseSpeedControllerWrapper::GetMotorSimulator()
{
    return mMotorSimulator;
}

void BaseSpeedControllerWrapper::SetFeedbackSensor(const std::shared_ptr<IFeedbackSensor>& aFeedbackSensor)
{
    mFeedbackSensor = aFeedbackSensor;
}

const std::shared_ptr<IFeedbackSensor>& BaseSpeedControllerWrapper::GetFeedbackSensor()
{
    return mFeedbackSensor;
}

double BaseSpeedControllerWrapper::GetVoltagePercentage()
{
    return mMotorSimulator->GetVoltagePercentage();
}

void BaseSpeedControllerWrapper::SetVoltagePercentage(double aSpeed)
{
    mMotorSimulator->SetVoltagePercentage(aSpeed);
}

void BaseSpeedControllerWrapper::Update(double aWaitTime)
{
    mMotorSimulator->Update(aWaitTime);
    mFeedbackSensor->SetPosition(GetPosition());
    mFeedbackSensor->SetVelocity(GetVelocity());
}

double BaseSpeedControllerWrapper::GetPosition()
{
    return mMotorSimulator->GetPosition();
}

double BaseSpeedControllerWrapper::GetVelocity()
{
    return mMotorSimulator->GetVelocity();
}

double BaseSpeedControllerWrapper::GetCurrent()
{
    return mMotorSimulator->GetCurrent();
}

double BaseSpeedControllerWrapper::GetAcceleration()
{
    return mMotorSimulator->GetAcceleration();
}

void BaseSpeedControllerWrapper::Reset()
{
    mMotorSimulator->Reset();
}

void BaseSpeedControllerWrapper::Reset(double aPosition, double aVelocity,
        double aCurrent)
{
    mMotorSimulator->Reset(aPosition, aVelocity, aCurrent);
}
