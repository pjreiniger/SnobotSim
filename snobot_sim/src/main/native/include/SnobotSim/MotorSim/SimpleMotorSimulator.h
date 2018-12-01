/*
 * SimpleMotorSimulator.h
 *
 *  Created on: May 5, 2017
 *      Author: PJ
 */

#pragma once
#include <string>

#include "SnobotSim/MotorSim/IMotorSimulator.h"

class EXPORT_ SimpleMotorSimulator : public IMotorSimulator
{
public:
    explicit SimpleMotorSimulator(double aMaxSpeed);
    virtual ~SimpleMotorSimulator();

    const std::string& GetSimulatorType() override;

    void SetVoltagePercentage(double aSpeed) override;

    double GetVoltagePercentage() override;

    double GetAcceleration() override;

    double GetVelocity() override;

    double GetPosition() override;

    double GetCurrent() override;

    void Reset() override;

    void Reset(double aPosition, double aVelocity, double aCurrent) override;

    void Update(double aCycleTime) override;

    double GetMaxSpeed();

protected:
    double mMaxSpeed;

    double mVoltagePercent;
    double mVelocity;
    double mPosition;

    static const std::string SIMULATOR_TYPE;
};
