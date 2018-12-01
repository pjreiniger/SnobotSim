/*
 * SpiNavxSimulator.h
 *
 *  Created on: Jul 9, 2017
 *      Author: PJ
 */

#pragma once

#include "NavxSim/NavxSimulator.h"

class EXPORT_ SpiNavxSimulator : public NavxSimulator
{
public:
    explicit SpiNavxSimulator(int aPort);
    virtual ~SpiNavxSimulator();

    void HandleRead(uint8_t* buffer, uint32_t count);
    void HandleWrite(const uint8_t* buffer, uint32_t count);

protected:
    uint8_t GetCRC(uint8_t* buffer, int length);

    uint8_t mLastWriteAddress;

    int32_t mPort;
    int32_t mReadCallbackId;
    int32_t mWriteCallbackId;
};
