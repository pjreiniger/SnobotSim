/*
 * I2CNavxSimulator.h
 *
 *  Created on: Jul 9, 2017
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SIM_EXTENSION_NAVX_SRC_MAIN_NATIVE_INCLUDE_NAVXSIM_I2CNAVXSIMULATOR_H_
#define SNOBOTSIM_SIM_EXTENSION_NAVX_SRC_MAIN_NATIVE_INCLUDE_NAVXSIM_I2CNAVXSIMULATOR_H_

#include "NavxSim/NavxSimulator.h"

class EXPORT_ I2CNavxSimulator : public NavxSimulator
{
public:
    explicit I2CNavxSimulator(int port);
    virtual ~I2CNavxSimulator();

    virtual void HandleRead(uint8_t* buffer, uint32_t count);
    virtual void HandleWrite(const uint8_t* buffer, uint32_t count);

protected:
    uint8_t mLastWriteAddress;

    int32_t mPort;
    int32_t mReadCallbackId;
    int32_t mWriteCallbackId;
};

#endif // SNOBOTSIM_SIM_EXTENSION_NAVX_SRC_MAIN_NATIVE_INCLUDE_NAVXSIM_I2CNAVXSIMULATOR_H_
