package com.snobot.simulator.gui.widgets;

import java.util.function.Supplier;

public interface IWidgetController
{

    void initialize(int aId);

    void update();

    void openSettings();

    boolean saveSettings();

    void setSaveAction(Supplier<Boolean> aSaveFunction);

}
