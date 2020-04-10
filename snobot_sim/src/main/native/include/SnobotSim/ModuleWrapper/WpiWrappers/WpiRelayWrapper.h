/*
 * RelayWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once

#include <string>

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IRelayWrapper.h"

class WpiRelayWrapper : public AModuleWrapper, public IRelayWrapper
{
public:
    static const std::string TYPE;

    explicit WpiRelayWrapper(int aPort);
    virtual ~WpiRelayWrapper();

    bool GetRelayForwards() override;
    bool GetRelayReverse() override;

    std::string GetType() override
    {
        return TYPE;
    }

protected:
    const int mHandle;
};
