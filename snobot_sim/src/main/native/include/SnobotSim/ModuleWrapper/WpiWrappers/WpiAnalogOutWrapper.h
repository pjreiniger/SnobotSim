/*
 * AnalogSourceWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once

#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAnalogOutWrapper.h"

class WpiAnalogOutWrapper : public AModuleWrapper, public IAnalogOutWrapper
{
public:
    static const std::string TYPE;

    explicit WpiAnalogOutWrapper(int aPort);
    virtual ~WpiAnalogOutWrapper();

    void SetVoltage(double aVoltage) override;
    double GetVoltage() override;

    std::string GetType() override
    {
        return TYPE;
    }

protected:
    const int mHandle;
};
