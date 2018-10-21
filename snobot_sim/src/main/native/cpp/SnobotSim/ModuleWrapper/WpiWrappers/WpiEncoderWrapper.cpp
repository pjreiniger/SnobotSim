/*
 * EncoderWrapper.cpp
 *
 *  Created on: May 4, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiEncoderWrapper.h"

#include "SnobotSim/PortUnwrapper.h"
#include "mockdata/EncoderData.h"

WpiEncoderWrapper::WpiEncoderWrapper(int aPortA, int aPortB) :
        WpiEncoderWrapper(aPortA, "Encoder " + std::to_string(UnwrapPort(aPortA)))
{
}

WpiEncoderWrapper::WpiEncoderWrapper(int aHandle, const std::string& aName) :
        AModuleWrapper(aName),
        mEncodingFactor(4),
        mDistancePerTick(1),
        mHandle(aHandle)
{
}

WpiEncoderWrapper::~WpiEncoderWrapper()
{
}

void WpiEncoderWrapper::Reset()
{
    if (mMotorWrapper)
    {
        mMotorWrapper->Reset();
    }
}

double WpiEncoderWrapper::GetDistance()
{
    if (mMotorWrapper)
    {
        return mMotorWrapper->GetPosition();
    }
    else
    {
        return 0;
    }
}

double WpiEncoderWrapper::GetPosition()
{
    return GetDistance();
}

void WpiEncoderWrapper::SetPosition(double aPosition)
{
    HALSIM_SetEncoderCount(mHandle, static_cast<int>(aPosition));
}

void WpiEncoderWrapper::SetVelocity(double aVelocity)
{
    HALSIM_SetEncoderPeriod(mHandle, 1.0 / aVelocity);
}

double WpiEncoderWrapper::GetVelocity()
{
    if (mMotorWrapper)
    {
        return mMotorWrapper->GetVelocity();
    }
    else
    {
        return 0;
    }
}

bool WpiEncoderWrapper::IsHookedUp()
{
    if (mMotorWrapper)
    {
        return true;
    }
    return false;
}

void WpiEncoderWrapper::SetSpeedController(const std::shared_ptr<ISpeedControllerWrapper>& aMotorWrapper)
{
    mMotorWrapper = aMotorWrapper;
    mMotorWrapper->SetFeedbackSensor(shared_from_this());
}

const std::shared_ptr<ISpeedControllerWrapper>& WpiEncoderWrapper::GetSpeedController()
{
    return mMotorWrapper;
}

void WpiEncoderWrapper::SetDistancePerTick(double aDPT)
{
    mDistancePerTick = aDPT;
}

double WpiEncoderWrapper::GetDistancePerTick()
{
    return mDistancePerTick;
}
