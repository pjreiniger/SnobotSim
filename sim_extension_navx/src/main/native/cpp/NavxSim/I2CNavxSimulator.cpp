/*
 * I2CNavxSimulator.cpp
 *
 *  Created on: Jul 9, 2017
 *      Author: PJ
 */

#include "NavxSim/I2CNavxSimulator.h"

#include "mockdata/I2CData.h"

static void NavxI2CReadBufferCallback(const char* name, void* param,
        uint8_t* buffer, uint32_t count)
{
    I2CNavxSimulator* sim = static_cast<I2CNavxSimulator*>(param);
    sim->HandleRead(buffer, count);
}

static void NavxI2CWriteBufferCallback(const char* name, void* param,
        const uint8_t* buffer, uint32_t count)
{
    I2CNavxSimulator* sim = static_cast<I2CNavxSimulator*>(param);
    sim->HandleWrite(buffer, count);
}

I2CNavxSimulator::I2CNavxSimulator(int port) :
        mPort(port)
{
    mReadCallbackId = HALSIM_RegisterI2CReadCallback(port, NavxI2CReadBufferCallback, this);
    mWriteCallbackId = HALSIM_RegisterI2CWriteCallback(port, NavxI2CWriteBufferCallback, this);
}

I2CNavxSimulator::~I2CNavxSimulator()
{
    HALSIM_CancelI2CReadCallback(mPort, mReadCallbackId);
    HALSIM_CancelI2CWriteCallback(mPort, mWriteCallbackId);
}

void I2CNavxSimulator::HandleWrite(const uint8_t* buffer, uint32_t count)
{
    mLastWriteAddress = buffer[0];
}

void I2CNavxSimulator::HandleRead(uint8_t* buffer, uint32_t count)
{
    if (mLastWriteAddress == 0x00)
    {
        GetWriteConfig(buffer);
        count = 17;
    }
    else if (mLastWriteAddress == 0x04 && count < 127)
    {
        GetCurrentData(buffer, 0x04);
        count = 86 - 0x04;
    }
}
