/*
 * GyroFactory.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include <string>

#include "SnobotSim/ExportHelper.h"

class EXPORT_ GyroFactory
{
public:
    GyroFactory();
    virtual ~GyroFactory();

    virtual bool Create(int aHandle, const std::string& aType);
};
