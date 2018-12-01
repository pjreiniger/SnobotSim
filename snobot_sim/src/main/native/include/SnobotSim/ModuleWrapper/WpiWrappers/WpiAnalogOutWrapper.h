/*
 * AnalogSourceWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IAnalogOutWrapper.h"

class WpiAnalogOutWrapper : public AModuleWrapper, public IAnalogOutWrapper
{
public:
    explicit WpiAnalogOutWrapper(int aPort);
    virtual ~WpiAnalogOutWrapper();

    void SetVoltage(double aVoltage) override;
    double GetVoltage() override;

protected:
    const int mHandle;
};
