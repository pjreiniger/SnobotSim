/*
 * EncoderWrapper.h
 *
 *  Created on: May 4, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPIENCODERWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPIENCODERWRAPPER_H_

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

    void Reset();

    double GetDistance();

    double GetVelocity();

    bool IsHookedUp();

    void SetSpeedController(const std::shared_ptr<ISpeedControllerWrapper>& aMotorWrapper);

    const std::shared_ptr<ISpeedControllerWrapper>& GetSpeedController();

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

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPIENCODERWRAPPER_H_
