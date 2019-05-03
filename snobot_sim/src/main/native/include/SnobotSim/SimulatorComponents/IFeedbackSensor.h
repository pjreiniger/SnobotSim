/*
 * IFeedbackSensor.hpp
 *
 *  Created on: Sep 10, 2017
 *      Author: PJ
 */

#pragma once
class IFeedbackSensor
{
public:
    virtual ~IFeedbackSensor() = default;

    virtual void SetPosition(double aPosition) = 0;
    virtual void SetVelocity(double aVelocity) = 0;
    virtual double GetPosition() = 0;
};

class NullFeedbackSensor : public IFeedbackSensor
{
public:
    virtual ~NullFeedbackSensor() = default;

    void SetPosition(double aPosition) override
    {
    }

    void SetVelocity(double aVelocity) override
    {
    }
    double GetPosition() override
    {
        return 0;
    }
};
