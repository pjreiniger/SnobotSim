/*
 * GravityLoadDcMotorSim.h
 *
 *  Created on: May 9, 2017
 *      Author: preiniger
 */

#pragma once

#include <string>

#include "SnobotSim/MotorSim/BaseDcMotorSimulator.h"

class EXPORT_ GravityLoadDcMotorSim : public BaseDcMotorSimulator
{
public:
    static std::string GetType()
    {
        return "Gravity Load";
    }
    std::string GetDisplaySimulatorType() override
    {
        return "Gravitational Load";
    }

    GravityLoadDcMotorSim(const DcMotorModel& aMotorModel, double aLoad);
    virtual ~GravityLoadDcMotorSim();

    void Update(double aCycleTime) override;

    double GetLoad();

protected:
    const double mLoad;

    static const double sGRAVITY;
};
