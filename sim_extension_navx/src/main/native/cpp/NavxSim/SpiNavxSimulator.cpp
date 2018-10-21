/*
 * SpiNavxSimulator.cpp
 *
 *  Created on: Jul 9, 2017
 *      Author: PJ
 */

#include "NavxSim/SpiNavxSimulator.h"

#include "mockdata/SPIData.h"

static void NavxSPIReadBufferCallback(const char* name, void* param,
        uint8_t* buffer, uint32_t count)
{
    SpiNavxSimulator* sim = static_cast<SpiNavxSimulator*>(param);
    sim->HandleRead(buffer, count);
}

static void NavxSPIWriteBufferCallback(const char* name, void* param,
        const uint8_t* buffer, uint32_t count)
{
    SpiNavxSimulator* sim = static_cast<SpiNavxSimulator*>(param);
    sim->HandleWrite(buffer, count);
}

SpiNavxSimulator::SpiNavxSimulator(int port) :
        NavxSimulator(),
        mPort(port)
{
    mReadCallbackId = HALSIM_RegisterSPIReadCallback(port, NavxSPIReadBufferCallback, this);
    mWriteCallbackId = HALSIM_RegisterSPIWriteCallback(port, NavxSPIWriteBufferCallback, this);
}

SpiNavxSimulator::~SpiNavxSimulator()
{
    HALSIM_CancelSPIReadCallback(mPort, mReadCallbackId);
    HALSIM_CancelSPIWriteCallback(mPort, mWriteCallbackId);
}

void SpiNavxSimulator::HandleWrite(const uint8_t* buffer, uint32_t count)
{
    mLastWriteAddress = buffer[0];
}

void SpiNavxSimulator::HandleRead(uint8_t* buffer, uint32_t count)
{
    mLastWriteAddress = 0x04;
    if (mLastWriteAddress == 0x00)
    {
        GetWriteConfig(buffer);
        count = 17 + 1;
    }
    else if (mLastWriteAddress == 0x04)
    {
        GetCurrentData(buffer, 0x04);
        count = 86 - 0x04 + 1;
    }

    buffer[count - 1] = GetCRC(buffer, count - 1);
}

uint8_t SpiNavxSimulator::GetCRC(uint8_t* buffer, int length)
{
    int i, j;
    uint8_t crc = 0;

    for (i = 0; i < length; i++)
    {
        crc ^= 0x00ff & buffer[i];
        for (j = 0; j < 8; j++)
        {
            if ((crc & 0x0001) != 0)
            {
                crc ^= 0x0091;
            }
            crc >>= 1;
        }
    }
    return crc;
}
