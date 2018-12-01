/*
 * CompressorWrapper.h
 *
 *  Created on: Jun 22, 2017
 *      Author: preiniger
 */

#pragma once
#include "SnobotSim/ExportHelper.h"

class EXPORT_ CompressorWrapper
{
public:
    CompressorWrapper();
    virtual ~CompressorWrapper();

    bool IsPressureSwitchFull();
};
