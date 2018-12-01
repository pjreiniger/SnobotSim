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
#include "SnobotSim/ModuleWrapper/Interfaces/IEncoderWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpeedControllerWrapper.h"
#include "SnobotSim/SimulatorComponents/IFeedbackSensor.h"

class WpiEncoderWrapper : public std::enable_shared_from_this<WpiEncoderWrapper>,
                          public AModuleWrapper,
                          public IFeedbackSensor,
                          public IEncoderWrapper
{
public:
    WpiEncoderWrapper(int aPortA, int aPortB);
    WpiEncoderWrapper(int aHandle, const std::string& aName);
    virtual ~WpiEncoderWrapper();

    void Reset() override;

    double GetDistance() override;

    double GetVelocity() override;

    bool IsHookedUp() override;

    void SetSpeedController(const std::shared_ptr<ISpeedControllerWrapper>& aMotorWrapper) override;

    const std::shared_ptr<ISpeedControllerWrapper>& GetSpeedController() override;

    void SetDistancePerTick(double aDPT);

    double GetDistancePerTick();

    double GetPosition() override;

    void SetPosition(double aPosition) override;

    void SetVelocity(double aVelocity) override;

protected:
    std::shared_ptr<ISpeedControllerWrapper> mMotorWrapper;
    double mEncodingFactor;
    double mDistancePerTick;
    const int mHandle;
};
