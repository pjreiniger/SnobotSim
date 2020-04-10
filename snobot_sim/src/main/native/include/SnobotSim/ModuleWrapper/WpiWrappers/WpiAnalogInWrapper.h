/*
 * AnalogSourceWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once

#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAnalogInWrapper.h"

class WpiAnalogInWrapper : public AModuleWrapper, public IAnalogInWrapper
{
public:
    static const std::string TYPE;

    explicit WpiAnalogInWrapper(int aPort);
    virtual ~WpiAnalogInWrapper();

    void SetVoltage(double aVoltage) override;
    double GetVoltage() override;

    const std::string& GetType() override
    {
        return TYPE;
    }

protected:
    const int mHandle;
};
