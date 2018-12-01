/*
 * RelayWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IRelayWrapper.h"

class WpiRelayWrapper : public AModuleWrapper, public IRelayWrapper
{
public:
    explicit WpiRelayWrapper(int aPort);
    virtual ~WpiRelayWrapper();

    bool GetRelayForwards() override;
    bool GetRelayReverse() override;

protected:
    const int mHandle;
};
