/*
 * EncoderWrapper.h
 *
 *  Created on: May 4, 2017
 *      Author: PJ
 */

#pragma once

#include <memory>
#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/BaseEncoderWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IEncoderWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpeedControllerWrapper.h"
#include "SnobotSim/SimulatorComponents/IFeedbackSensor.h"

class WpiEncoderWrapper : public BaseEncoderWrapper
{
public:
    static const std::string TYPE;

    WpiEncoderWrapper(int aPortA, int aPortB);
    WpiEncoderWrapper(int aHandle, const std::string& aName);
    virtual ~WpiEncoderWrapper();

    void Reset() override;

    void SetDistancePerTick(double aDPT);

    double GetDistancePerTick();

    void SetPosition(double aPosition) override;

    void SetVelocity(double aVelocity) override;

    const std::string& GetType() override
    {
        return TYPE;
    }

protected:
    double mEncodingFactor;
    double mDistancePerTick;
    const int mHandle;
};
