/*
 * ISpeedControllerWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include <memory>

#include "SnobotSim/ModuleWrapper/Interfaces/ISensorWrapper.h"
#include "SnobotSim/MotorSim/IMotorSimulator.h"
#include "SnobotSim/SimulatorComponents/IFeedbackSensor.h"

class ISpeedControllerWrapper : public virtual ISensorWrapper
{
public:
    virtual int GetId() = 0;

    virtual void SetMotorSimulator(const std::shared_ptr<IMotorSimulator>& aSimulator) = 0;

    virtual const std::shared_ptr<IMotorSimulator>& GetMotorSimulator() = 0;

    virtual void SetFeedbackSensor(const std::shared_ptr<IFeedbackSensor>& aSimulator) = 0;

    virtual const std::shared_ptr<IFeedbackSensor>& GetFeedbackSensor() = 0;

    virtual double GetVoltagePercentage() = 0;

    virtual void SetVoltagePercentage(double aSpeed) = 0;

    virtual void Update(double aWaitTime) = 0;

    virtual double GetPosition() = 0;

    virtual double GetVelocity() = 0;

    virtual double GetCurrent() = 0;

    virtual double GetAcceleration() = 0;

    virtual void Reset() = 0;

    virtual void Reset(double aPosition, double aVelocity, double aCurrent) = 0;
};
