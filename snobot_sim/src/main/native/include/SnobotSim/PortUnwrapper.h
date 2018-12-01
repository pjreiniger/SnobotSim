/*
 * PortUnwrapper.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#pragma once
inline int UnwrapPort(int aInPort)
{
    int output;

    if ((aInPort & 0x2000000) == 0)
    {
        output = aInPort;
    }
    else
    {
        output = aInPort & 0xFF;
    }

    return output;
}

inline int WrapPort(int aInPort)
{
    int output = 0x2000100;
    output += aInPort;

    return output;
}
