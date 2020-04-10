
#pragma once

#include <string>

class RevManager
{
public:
    void Reset();

    void handleMessage(const std::string& aCallback, int aCanPort, uint8_t* aBuffer, int aLength);
};
