/*
 * ISensorWrapper.h
 *
 *  Created on: Jun 30, 2018
 *      Author: PJ
 */

#pragma once

#include <string>

class ISensorWrapper
{
public:
    virtual bool IsInitialized() = 0;

    virtual void SetInitialized(bool aIsInitialized) = 0;

    virtual std::string GetType() = 0;

    virtual const std::string& GetName() = 0;

    virtual void SetName(const std::string& aName) = 0;

    virtual bool WantsHidden() = 0;

    virtual void SetWantsHidden(bool aVisible) = 0;
};
