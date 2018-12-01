/*
 * DigitalSourceWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once

#include "SnobotSim/ModuleWrapper/AModuleWrapper.h"
#include "SnobotSim/ModuleWrapper/Interfaces/IDigitalIoWrapper.h"

class EXPORT_ WpiDigitalIoWrapper : public AModuleWrapper, public IDigitalIoWrapper
{
public:
    explicit WpiDigitalIoWrapper(int aPort);
    virtual ~WpiDigitalIoWrapper();

    bool Get() override;
    void Set(bool aState) override;

protected:
    const int mHandle;
};
