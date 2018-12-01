/*
 * RelayFactory.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include <string>

#include "SnobotSim/ExportHelper.h"

class EXPORT_ RelayFactory
{
public:
    RelayFactory();
    virtual ~RelayFactory();

    virtual bool Create(int aHandle, const std::string& aType);
};
