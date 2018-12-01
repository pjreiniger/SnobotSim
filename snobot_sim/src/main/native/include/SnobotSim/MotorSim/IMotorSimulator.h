/*
 * IMotorSimulator.hpp
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once
#include <string>

#include "SnobotSim/ExportHelper.h"

class EXPORT_ IMotorSimulator
{
public:
    virtual ~IMotorSimulator()
    {
    }

    virtual const std::string& GetSimulatorType() = 0;

    virtual void SetVoltagePercentage(double aSpeed) = 0;

    virtual double GetVoltagePercentage() = 0;

    virtual double GetAcceleration() = 0;

    virtual double GetVelocity() = 0;

    virtual double GetPosition() = 0;

    virtual double GetCurrent() = 0;

    virtual void Reset() = 0;

    virtual void Reset(double aPosition, double aVelocity, double aCurrent) = 0;

    virtual void Update(double aCycleTime) = 0;
};

class NullMotorSimulator : public IMotorSimulator
{
public:
    NullMotorSimulator() :
            mSpeed(0)
    {
    }

    virtual ~NullMotorSimulator()
    {
    }

    const std::string& GetSimulatorType() override
    {
        static std::string sName = "Null";
        return sName;
    }

    void SetVoltagePercentage(double aSpeed) override
    {
        mSpeed = aSpeed;
    }

    double GetVoltagePercentage() override
    {
        return mSpeed;
    }

    double GetAcceleration() override
    {
        return 0;
    }

    double GetVelocity() override
    {
        return 0;
    }

    double GetPosition() override
    {
        return 0;
    }

    double GetCurrent() override
    {
        return 0;
    }

    void Reset() override
    {
    }

    void Reset(double aPosition, double aVelocity, double aCurrent) override
    {
    }

    void Update(double aCycleTime) override
    {
    }

protected:
    double mSpeed;
};
