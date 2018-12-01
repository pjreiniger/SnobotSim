/*
 * SolenoidFactory.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include <string>

#include "SnobotSim/ExportHelper.h"

class EXPORT_ SolenoidFactory
{
public:
    SolenoidFactory();
    virtual ~SolenoidFactory();

    virtual bool Create(int aHandle, const std::string& aType);
};
