/*
 * BaseDcMotorSimulator.hpp
 *
 *  Created on: May 9, 2017
 *      Author: preiniger
 */

#pragma once

#include <string>

#include "SnobotSim/MotorSim/DcMotorModel.h"
#include "SnobotSim/MotorSim/IMotorSimulator.h"

class EXPORT_ BaseDcMotorSimulator : public IMotorSimulator
{
public:
    BaseDcMotorSimulator(
            const std::string& aSimulatorType,
            const DcMotorModel& aMotorModel,
            double aConversionFactor = 1);
    virtual ~BaseDcMotorSimulator();

    const std::string& GetSimulatorType() override;

    void SetVoltagePercentage(double aSpeed) override;

    double GetVoltagePercentage() override;

    double GetAcceleration() override;

    double GetVelocity() override;

    double GetPosition() override;

    double GetCurrent() override;

    void Reset() override;

    void Reset(double aPosition, double aVelocity, double aCurrent) override;

    double GetConversionFactor();

    virtual const DcMotorModel& GetMotorModel();

protected:
    std::string mSimulatorType;
    DcMotorModel mMotorModel;
    const double mConversionFactor;
    double mVoltagePercentage;
};
