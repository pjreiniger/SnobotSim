/*
 * SpiNavxSimulator.h
 *
 *  Created on: Jul 9, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SIM_EXTENSION_NAVX_SRC_MAIN_NATIVE_INCLUDE_NAVXSIM_SPINAVXSIMULATOR_H_
#define SNOBOTSIM_SIM_EXTENSION_NAVX_SRC_MAIN_NATIVE_INCLUDE_NAVXSIM_SPINAVXSIMULATOR_H_

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

#endif // SNOBOTSIM_SIM_EXTENSION_NAVX_SRC_MAIN_NATIVE_INCLUDE_NAVXSIM_SPINAVXSIMULATOR_H_
