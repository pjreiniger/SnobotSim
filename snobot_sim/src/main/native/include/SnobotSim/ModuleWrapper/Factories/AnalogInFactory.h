/*
 * AnalogInFactory.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include <string>

#include "SnobotSim/ExportHelper.h"

class EXPORT_ AnalogInFactory
{
public:
    AnalogInFactory();
    virtual ~AnalogInFactory();

    virtual bool Create(int aHandle, const std::string& aType);
};
