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

    virtual int GetId();

    virtual void SetMotorSimulator(const std::shared_ptr<IMotorSimulator>& aSimulator);

    virtual const std::shared_ptr<IMotorSimulator>& GetMotorSimulator();

    virtual void SetFeedbackSensor(const std::shared_ptr<IFeedbackSensor>& aSimulator);

    virtual const std::shared_ptr<IFeedbackSensor>& GetFeedbackSensor();

    virtual double GetVoltagePercentage();

    virtual void SetVoltagePercentage(double aSpeed);

    virtual void Update(double aWaitTime);

    virtual double GetPosition();

    virtual double GetVelocity();

    virtual double GetCurrent();

    virtual double GetAcceleration();

    virtual void Reset();

    virtual void Reset(double aPosition, double aVelocity, double aCurrent);

protected:
    int mId;
    std::shared_ptr<IMotorSimulator> mMotorSimulator;
    std::shared_ptr<IFeedbackSensor> mFeedbackSensor;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_WPIWRAPPERS_WPISPEEDCONTROLLERWRAPPER_H_
