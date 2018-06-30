package com.snobot.simulator.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.snobot.simulator.gui.module_widget.AccelerometerOutputDisplay;
import com.snobot.simulator.gui.module_widget.AnalogInputDisplay;
import com.snobot.simulator.gui.module_widget.AnalogOutputDisplay;
import com.snobot.simulator.gui.module_widget.BaseWidgetDisplay;
import com.snobot.simulator.gui.module_widget.DigitalSourceGraphicDisplay;
import com.snobot.simulator.gui.module_widget.EncoderGraphicDisplay;
import com.snobot.simulator.gui.module_widget.GyroGraphicDisplay;
import com.snobot.simulator.gui.module_widget.RelayGraphicDisplay;
import com.snobot.simulator.gui.module_widget.SolenoidGraphicDisplay;
import com.snobot.simulator.gui.module_widget.SpeedControllerGraphicDisplay;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

/**
 * This is the top level panel that shows all of the simulator components
 *
 * @author PJ
 *
 */
public class GraphicalSensorDisplayPanel extends JPanel
{
    public GraphicalSensorDisplayPanel()
    {
        create();
    }

    protected List<BaseWidgetDisplay<?, ?>> mDisplayPanels;
    protected AdvancedSettingsPanel mAdvancedSettingsPanel;

    private void create()
    {
        mDisplayPanels = new ArrayList<>();

        mDisplayPanels.add(new SpeedControllerGraphicDisplay(DataAccessorFactory.getInstance().getSpeedControllerAccessor().getPortList()));
        mDisplayPanels.add(new SolenoidGraphicDisplay(DataAccessorFactory.getInstance().getSolenoidAccessor().getPortList()));
        mDisplayPanels.add(new DigitalSourceGraphicDisplay(DataAccessorFactory.getInstance().getDigitalAccessor().getPortList()));
        mDisplayPanels.add(new RelayGraphicDisplay(DataAccessorFactory.getInstance().getRelayAccessor().getPortList()));
        mDisplayPanels.add(new AnalogInputDisplay(DataAccessorFactory.getInstance().getAnalogInAccessor().getPortList()));
        mDisplayPanels.add(new AnalogOutputDisplay(DataAccessorFactory.getInstance().getAnalogOutAccessor().getPortList()));
        mDisplayPanels.add(new EncoderGraphicDisplay(DataAccessorFactory.getInstance().getEncoderAccessor().getPortList()));
        mDisplayPanels.add(new GyroGraphicDisplay(DataAccessorFactory.getInstance().getGyroAccessor().getPortList()));
        mDisplayPanels.add(new AccelerometerOutputDisplay(DataAccessorFactory.getInstance().getAccelerometerAccessor().getPortList()));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (BaseWidgetDisplay<?, ?> panel : mDisplayPanels)
        {
            if (!panel.isEmpty())
            {
                add(panel);
            }
        }

        mAdvancedSettingsPanel = new AdvancedSettingsPanel();
        add(mAdvancedSettingsPanel);
    }

    public void update()
    {
        for (BaseWidgetDisplay<?, ?> panel : mDisplayPanels)
        {
            panel.update();
        }

        repaint();
    }

    public void showSettingsButtons(boolean aShow)
    {
        for (BaseWidgetDisplay<?, ?> panel : mDisplayPanels)
        {
            panel.showSettingsButtons(aShow);
        }

        mAdvancedSettingsPanel.showSettingsButtons(aShow);
    }
}
