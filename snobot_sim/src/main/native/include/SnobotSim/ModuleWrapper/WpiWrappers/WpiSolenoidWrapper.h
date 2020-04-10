/*
 * SolenoidWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once

#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/ISolenoidWrapper.h"

class WpiSolenoidWrapper : public AModuleWrapper, public ISolenoidWrapper
{
public:
    static const std::string TYPE;

    explicit WpiSolenoidWrapper(int aPort);
    virtual ~WpiSolenoidWrapper();

    void SetState(bool aOn) override;

    bool GetState() override;

    const std::string& GetType() override
    {
        return TYPE;
    }

protected:
    const int mModule;
    const int mChannel;
};
