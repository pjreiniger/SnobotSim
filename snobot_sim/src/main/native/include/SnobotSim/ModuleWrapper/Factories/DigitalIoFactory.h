/*
 * DigitalIoFactory.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include <string>

#include "SnobotSim/ExportHelper.h"

class EXPORT_ DigitalIoFactory
{
public:
    DigitalIoFactory();
    virtual ~DigitalIoFactory();

    virtual bool Create(int aHandle, const std::string& aType);
};
