/*
 * SpeedControllerWrapper.h
 *
 *  Created on: May 3, 2017
 *      Author: PJ
 */

#pragma once

#include <memory>
#include <string>

#include "SnobotSim/ModuleWrapper/BaseSpeedControllerWrapper.h"

class WpiSpeedControllerWrapper : public BaseSpeedControllerWrapper
{
public:
    static const std::string TYPE;

    explicit WpiSpeedControllerWrapper(int aPort);
    virtual ~WpiSpeedControllerWrapper();

    std::string GetType() override
    {
        return TYPE;
    }
};
