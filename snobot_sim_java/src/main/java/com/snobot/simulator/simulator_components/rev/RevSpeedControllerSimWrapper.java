package com.snobot.simulator.simulator_components.rev;

import com.snobot.simulator.module_wrapper.BasePwmWrapper;
import com.snobot.simulator.simulator_components.ctre.CtreTalonSrxSpeedControllerSim;

public class RevSpeedControllerSimWrapper extends BasePwmWrapper
{

    public RevSpeedControllerSimWrapper(int aCanHandle)
    {
        super(aCanHandle, "Rev SC " + (aCanHandle - CtreTalonSrxSpeedControllerSim.sCTRE_OFFSET));
    }

}
