/*
 * IFeedbackSensor.hpp
 *
 *  Created on: Sep 10, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_IFEEDBACKSENSOR_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_IFEEDBACKSENSOR_H_

class IFeedbackSensor
{
public:
    virtual void SetPosition(double aPosition) = 0;
    virtual void SetVelocity(double aVelocity) = 0;
    virtual double GetPosition() = 0;
};

class NullFeedbackSensor : public IFeedbackSensor
{
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

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_SIMULATORCOMPONENTS_IFEEDBACKSENSOR_H_
