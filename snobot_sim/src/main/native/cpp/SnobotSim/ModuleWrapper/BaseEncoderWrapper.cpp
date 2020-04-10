
#include "SnobotSim/ModuleWrapper/BaseEncoderWrapper.h"

#include <iostream>

BaseEncoderWrapper::BaseEncoderWrapper(const std::string& aName) :
        AModuleWrapper(aName)
{
}

BaseEncoderWrapper::~BaseEncoderWrapper()
{
}

void BaseEncoderWrapper::Reset()
{
    std::cout << "Ressetting" << std::endl;
    if (mMotorWrapper)
    {
        mMotorWrapper->Reset();
    }

    mPosition = 0;
    mVelocity = 0;
}

void BaseEncoderWrapper::SetPosition(double aPosition)
{
    mPosition = aPosition;
}

void BaseEncoderWrapper::SetVelocity(double aVelocity)
{
    mVelocity = aVelocity;
}

double BaseEncoderWrapper::GetDistance()
{
    return mPosition;
}

double BaseEncoderWrapper::GetPosition()
{
    return GetDistance();
}

double BaseEncoderWrapper::GetVelocity()
{
    return mVelocity;
}

bool BaseEncoderWrapper::IsHookedUp()
{
    return static_cast<bool>(mMotorWrapper);
}

void BaseEncoderWrapper::SetSpeedController(const std::shared_ptr<ISpeedControllerWrapper>& aMotorWrapper)
{
    std::cout << "Setting speed controller..." << std::endl;
    mMotorWrapper = aMotorWrapper;
    mMotorWrapper->SetFeedbackSensor(shared_from_this());
}

const std::shared_ptr<ISpeedControllerWrapper>& BaseEncoderWrapper::GetSpeedController()
{
    return mMotorWrapper;
}
