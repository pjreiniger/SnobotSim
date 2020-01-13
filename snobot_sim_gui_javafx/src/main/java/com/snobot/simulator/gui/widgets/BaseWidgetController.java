package com.snobot.simulator.gui.widgets;

import java.util.function.Supplier;

public abstract class BaseWidgetController implements IWidgetController
{
    private Supplier<Boolean> mSaveSettingsFunction;

    @Override
    public boolean saveSettings()
    {
        return mSaveSettingsFunction.get();
    }

    @Override
    public void setSaveAction(Supplier<Boolean> aSaveFunction)
    {
        mSaveSettingsFunction = aSaveFunction;
    }

}
