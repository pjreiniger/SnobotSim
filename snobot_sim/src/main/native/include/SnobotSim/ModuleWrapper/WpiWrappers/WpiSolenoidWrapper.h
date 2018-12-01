/*
 * SolenoidWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISolenoidWrapper.h"

class WpiSolenoidWrapper : public AModuleWrapper, public ISolenoidWrapper
{
public:
    explicit WpiSolenoidWrapper(int aPort);
    virtual ~WpiSolenoidWrapper();

    void SetState(bool aOn) override;

    bool GetState() override;

protected:
    const int mModule;
    const int mChannel;
};
