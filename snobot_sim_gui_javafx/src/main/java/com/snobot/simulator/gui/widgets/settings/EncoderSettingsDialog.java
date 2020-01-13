package com.snobot.simulator.gui.widgets.settings;

import java.util.List;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class EncoderSettingsDialog extends BasicSettingsDialog
{
    @FXML
    private ComboBox<SensorHandleOption> mSpeedControllerComboBox;

    @FXML
    public void initialize()
    {
        List<Integer> speedControllers = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList();
        for (int handle : speedControllers)
        {
            SensorHandleOption option = new SensorHandleOption(handle,
                    DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(handle));
            mSpeedControllerComboBox.getItems().add(option);
        }
    }

    public void setEncoderHandle(int aEncoderHandle)
    {
        int connectedSc = -1;
        if (DataAccessorFactory.getInstance().getEncoderAccessor().isHookedUp(aEncoderHandle))
        {
            connectedSc = DataAccessorFactory.getInstance().getEncoderAccessor().getHookedUpId(aEncoderHandle);
        }

        for (int i = 0; i < mSpeedControllerComboBox.getItems().size(); ++i)
        {
            SensorHandleOption option = mSpeedControllerComboBox.getItems().get(i);

            if (option.mHandle == connectedSc)
            {
                mSpeedControllerComboBox.getSelectionModel().select(i);
            }
            if (option.mHandle != -1)
            {
                option.mName = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getName(option.mHandle);
            }
        }
    }

    public int getSelectedId()
    {
        SensorHandleOption option = (SensorHandleOption) mSpeedControllerComboBox.getSelectionModel().getSelectedItem();
        return option == null ? -1 : option.mHandle;
    }
}
