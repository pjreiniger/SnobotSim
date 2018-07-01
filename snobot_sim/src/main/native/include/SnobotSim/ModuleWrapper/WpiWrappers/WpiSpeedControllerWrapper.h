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

    virtual int GetId() override;

    virtual void SetMotorSimulator(const std::shared_ptr<IMotorSimulator>& aSimulator) override;

    virtual const std::shared_ptr<IMotorSimulator>& GetMotorSimulator() override;

    virtual void SetFeedbackSensor(const std::shared_ptr<IFeedbackSensor>& aSimulator) override;

    virtual const std::shared_ptr<IFeedbackSensor>& GetFeedbackSensor() override;

    virtual double GetVoltagePercentage() override;

    virtual void SetVoltagePercentage(double aSpeed) override;

    virtual void Update(double aWaitTime) override;

    virtual double GetPosition() override;

    virtual double GetVelocity() override;

    virtual double GetCurrent() override;

    virtual double GetAcceleration() override;

    virtual void Reset() override;

    virtual void Reset(double aPosition, double aVelocity, double aCurrent) override;

protected:
    int mId;
    std::shared_ptr<IMotorSimulator> mMotorSimulator;
    std::shared_ptr<IFeedbackSensor> mFeedbackSensor;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPISPEEDCONTROLLERWRAPPER_H_
