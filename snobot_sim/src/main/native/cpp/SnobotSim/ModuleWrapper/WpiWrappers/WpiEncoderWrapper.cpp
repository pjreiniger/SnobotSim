/*
 * EncoderWrapper.cpp
 *
 *  Created on: May 4, 2017
 *      Author: PJ
 */

#include "SnobotSim/ModuleWrapper/WpiWrappers/WpiEncoderWrapper.h"

#include "SnobotSim/PortUnwrapper.h"
#include "mockdata/EncoderData.h"

const std::string WpiEncoderWrapper::TYPE = "com.snobot.simulator.module_wrapper.wpi.WpiEncoderWrapper";

WpiEncoderWrapper::WpiEncoderWrapper(int aPortA, int aPortB) :
        WpiEncoderWrapper(aPortA, "Encoder " + std::to_string(UnwrapPort(aPortA)))
{
}

WpiEncoderWrapper::WpiEncoderWrapper(int aHandle, const std::string& aName) :
        BaseEncoderWrapper(aName),
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
    BaseEncoderWrapper::Reset();
    HALSIM_SetEncoderReset(mHandle, true);
    HALSIM_SetEncoderReset(mHandle, false);
}

void WpiEncoderWrapper::SetPosition(double aPosition)
{
    BaseEncoderWrapper::SetPosition(aPosition);
    HALSIM_SetEncoderCount(mHandle, static_cast<int>(aPosition));
}

void WpiEncoderWrapper::SetVelocity(double aVelocity)
{
    BaseEncoderWrapper::SetVelocity(aVelocity);
    HALSIM_SetEncoderPeriod(mHandle, 1.0 / aVelocity);
}

void WpiEncoderWrapper::SetDistancePerTick(double aDPT)
{
    mDistancePerTick = aDPT;
}

double WpiEncoderWrapper::GetDistancePerTick()
{
    return mDistancePerTick;
}
