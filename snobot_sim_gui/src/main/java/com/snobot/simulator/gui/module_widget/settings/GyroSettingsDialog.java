package com.snobot.simulator.gui.module_widget.settings;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

public class GyroSettingsDialog extends SimpleSettingsDialog
{
    protected JComboBox<SensorHandleOption> mSpeedControllerSelection;

    public GyroSettingsDialog(String aTitle, int aKey, String aName)
    {
        super(aTitle, aKey, aName);

        mSpeedControllerSelection = new JComboBox<>();
        mSpeedControllerSelection.addItem(new SensorHandleOption(-1, "None"));

        List<Integer> speedControllers = DataAccessorFactory.getInstance().getGyroAccessor().getPortList();
        for (int handle : speedControllers)
        {
            SensorHandleOption option = new SensorHandleOption(handle, DataAccessorFactory.getInstance().getGyroAccessor().getWrapper(handle).getName());
            mSpeedControllerSelection.addItem(option);
        }

        JPanel scSelectionPanel = new JPanel();
        scSelectionPanel.add(new JLabel("Connected SC"));
        scSelectionPanel.add(mSpeedControllerSelection);
        getContentPane().add(scSelectionPanel, BorderLayout.CENTER);
    }

    @Override
    protected void onSubmit()
    {
        // SensorHandleOption option = (SensorHandleOption) mSpeedControllerSelection.getSelectedItem();
        // int scId = option == null ? -1 : option.mHandle;
        //
        // EncoderWrapperJni.connectSpeedController(mHandle, scId);
    }

}
