/*
 * AnalogSourceWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAnalogInWrapper.h"

class WpiAnalogInWrapper : public AModuleWrapper, public IAnalogInWrapper
{
public:
    explicit WpiAnalogInWrapper(int aPort);
    virtual ~WpiAnalogInWrapper();

    void SetVoltage(double aVoltage) override;
    double GetVoltage() override;

protected:
    const int mHandle;
};
