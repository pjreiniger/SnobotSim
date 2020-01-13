package com.snobot.simulator.gui.widgets;

import java.util.function.Supplier;

import com.snobot.simulator.gui.widgets.settings.DialogRunner;
import com.snobot.simulator.gui.widgets.settings.advanced.SpiI2cSettingsController;
import com.snobot.simulator.gui.widgets.settings.advanced.TankDriveSettingsController;

import javafx.fxml.FXML;

public class AdvancedSettingsController
{
    private Supplier<Boolean> mSaveFunction;

    @FXML
    protected void handleSpiI2cButton()
    {
        DialogRunner<SpiI2cSettingsController> dialog = new DialogRunner<>("/com/snobot/simulator/gui/widgets/settings/advanced/spi_i2c_settings.fxml");
        if (dialog.showAndWait())
        {
            dialog.getController().onSubmit();
            mSaveFunction.get();
        }
    }

    @FXML
    protected void handleTankDriveButton()
    {
        DialogRunner<TankDriveSettingsController> dialog = new DialogRunner<>(
                "/com/snobot/simulator/gui/widgets/settings/advanced/tank_drive_settings.fxml");
        if (dialog.showAndWait())
        {
            dialog.getController().onSubmit();
            mSaveFunction.get();
        }
    }

    public void setSaveCallback(Supplier<Boolean> aSaveFunction)
    {
        mSaveFunction = aSaveFunction;
    }
}
