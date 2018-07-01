/*
 * SpeedControllerWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPISPEEDCONTROLLERWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPISPEEDCONTROLLERWRAPPER_H_

#include <memory>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISpeedControllerWrapper.h"
#include "SnobotSim/MotorSim/IMotorSimulator.h"
#include "SnobotSim/SimulatorComponents/IFeedbackSensor.h"

class WpiSpeedControllerWrapper : public AModuleWrapper, public ISpeedControllerWrapper
{
public:
    explicit WpiSpeedControllerWrapper(int aPort);
    virtual ~WpiSpeedControllerWrapper();

    int GetId() override;

    void SetMotorSimulator(const std::shared_ptr<IMotorSimulator>& aSimulator) override;

    const std::shared_ptr<IMotorSimulator>& GetMotorSimulator() override;

    void SetFeedbackSensor(const std::shared_ptr<IFeedbackSensor>& aSimulator) override;

    const std::shared_ptr<IFeedbackSensor>& GetFeedbackSensor() override;

    double GetVoltagePercentage() override;

    void SetVoltagePercentage(double aSpeed) override;

    void Update(double aWaitTime) override;

    double GetPosition() override;

    double GetVelocity() override;

    double GetCurrent() override;

    double GetAcceleration() override;

    void Reset() override;

    void Reset(double aPosition, double aVelocity, double aCurrent) override;

protected:
    int mId;
    std::shared_ptr<IMotorSimulator> mMotorSimulator;
    std::shared_ptr<IFeedbackSensor> mFeedbackSensor;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPISPEEDCONTROLLERWRAPPER_H_
