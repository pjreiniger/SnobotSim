/*
 * ISensorWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#ifndef SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_ISENSORWRAPPER_H_
#define SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_ISENSORWRAPPER_H_

#include <string>

class ISensorWrapper
{
public:
    virtual bool IsInitialized() = 0;

    virtual void SetInitialized(bool aIsInitialized) = 0;

    virtual const std::string& GetName() = 0;

    virtual void SetName(const std::string& aName) = 0;

    virtual bool WantsHidden() = 0;

    virtual void SetWantsHidden(bool aVisible) = 0;
};

#endif // SNOBOTSIM_SNOBOT_SIM_SRC_MAIN_NATIVE_INCLUDE_SNOBOTSIM_MODULEWRAPPER_INTERFACES_ISENSORWRAPPER_H_
