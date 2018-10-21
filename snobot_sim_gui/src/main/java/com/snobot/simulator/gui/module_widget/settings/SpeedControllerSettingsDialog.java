package com.snobot.simulator.gui.module_widget.settings;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.motor_sim.GravityLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.RotationalLoadMotorSimulationConfig;
import com.snobot.simulator.motor_sim.SimpleMotorSimulationConfig;
import com.snobot.simulator.motor_sim.StaticLoadMotorSimulationConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SpeedControllerWrapperAccessor.MotorSimType;

public class SpeedControllerSettingsDialog extends SimpleSettingsDialog
{
    protected final JPanel mSimOptionsPanel;
    protected final JComboBox<MotorSimType> mSimModeSelector;

    public SpeedControllerSettingsDialog(String aTitle, int aKey, String aName)
    {
        super(aTitle, aKey, aName);

        final JPanel nullSimPanel = new NullSimConfigPanel(mHandle);
        final JPanel simpleSimPanel = new SimpleSimConfigPanel(mHandle);
        final JPanel staticLoadSimPanel = new StaticLoadSimConfigPanel(mHandle);
        final JPanel rotationalLoadSimPanel = new RotationalLoadSimConfigPanel(mHandle);
        final JPanel gravitationalLoadSimPanel = new GravitationalLoadSimConfigPanel(mHandle);

        CardLayout simOptionsLayout = new CardLayout();
        mSimOptionsPanel = new JPanel(simOptionsLayout);
        mSimOptionsPanel.add(nullSimPanel, MotorSimType.None.toString());
        mSimOptionsPanel.add(simpleSimPanel, MotorSimType.Simple.toString());
        mSimOptionsPanel.add(staticLoadSimPanel, MotorSimType.StaticLoad.toString());
        mSimOptionsPanel.add(rotationalLoadSimPanel, MotorSimType.RotationalLoad.toString());
        mSimOptionsPanel.add(gravitationalLoadSimPanel, MotorSimType.GravitationalLoad.toString());

        mSimModeSelector = new JComboBox<>(MotorSimType.values());
        JPanel typeSelectionPanel = new JPanel();
        typeSelectionPanel.add(new JLabel("Simulation Type"));
        typeSelectionPanel.add(mSimModeSelector);

        JPanel simDisplayPanel = new JPanel(new BorderLayout());
        simDisplayPanel.add(typeSelectionPanel, BorderLayout.NORTH);
        simDisplayPanel.add(mSimOptionsPanel, BorderLayout.CENTER);

        getContentPane().add(simDisplayPanel, BorderLayout.CENTER);

        mSimModeSelector.addItemListener(new ItemListener()
        {

            @Override
            public void itemStateChanged(ItemEvent aEvent)
            {
                simOptionsLayout.show(mSimOptionsPanel, aEvent.getItem().toString());
            }
        });

        MotorSimType mode = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimType(mHandle);
        mSimModeSelector.setSelectedItem(mode);
    }

    @Override
    public void setVisible(boolean aVisible)
    {
        MotorSimType mode = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimType(mHandle);
        mSimModeSelector.setSelectedItem(mode);
        super.setVisible(aVisible);
    }

    @Override
    protected void onSubmit()
    {
        for (Component comp : mSimOptionsPanel.getComponents())
        {
            if (comp.isVisible())
            {
                SubmitableMotorSimulator motorSimulatorPanel = (SubmitableMotorSimulator) comp;
                motorSimulatorPanel.submit();
            }
        }
    }
}

interface SubmitableMotorSimulator
{
    public void submit();
}

class NullSimConfigPanel extends JPanel implements SubmitableMotorSimulator
{
    protected int mHandle;

    public NullSimConfigPanel(int aHandle)
    {
        this.mHandle = aHandle;
        add(new JLabel("Null simulator.  Position will not change"));
    }

    @Override
    public void submit()
    {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(mHandle, new SimpleMotorSimulationConfig(0));
    }

}

class SimpleSimConfigPanel extends JPanel implements SubmitableMotorSimulator
{
    protected int mHandle;
    protected JTextField mMaxSpeedField;

    public SimpleSimConfigPanel(int aHandle)
    {
        this.mHandle = aHandle;

        mMaxSpeedField = new JTextField(3);
        add(new JLabel("Max Speed"));
        add(mMaxSpeedField);
    }

    @Override
    public void submit()
    {
        double maxSpeed = Double.parseDouble(mMaxSpeedField.getText());
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Simple(mHandle,
                new SimpleMotorSimulationConfig(maxSpeed));
    }

    @Override
    public void setVisible(boolean aVisible)
    {
        if (aVisible)
        {
            SimpleMotorSimulationConfig config = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimSimpleModelConfig(mHandle);
            mMaxSpeedField.setText(Double.toString(config.mMaxSpeed));
        }

        super.setVisible(aVisible);
    }

}

abstract class MotorSimWithModelPanel extends JPanel implements SubmitableMotorSimulator
{
    protected DcMotorConfigPanel mMotorSimPanel;
    protected int mHandle;

    protected MotorSimWithModelPanel(int aHandle)
    {
        setLayout(new BorderLayout());
        mHandle = aHandle;
        mMotorSimPanel = new DcMotorConfigPanel();
        add(mMotorSimPanel, BorderLayout.CENTER);
    }

    @Override
    public void setVisible(boolean aVisible)
    {
        if (aVisible)
        {
            DcMotorModelConfig modelConfig = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorConfig(mHandle);
            mMotorSimPanel.setModelConfig(modelConfig);
            updateSimulatorParams();
        }

        super.setVisible(aVisible);
    }

    protected abstract void updateSimulatorParams();
}

class StaticLoadSimConfigPanel extends MotorSimWithModelPanel
{
    protected JTextField mLoadField;

    public StaticLoadSimConfigPanel(int aKey)
    {
        super(aKey);

        mLoadField = new JTextField(5);

        JPanel simPanel = new JPanel();
        simPanel.setBorder(new TitledBorder("Simulator Parameters"));
        simPanel.add(new JLabel("Load"));
        simPanel.add(mLoadField);

        add(simPanel, BorderLayout.NORTH);
    }

    @Override
    public void submit()
    {
        double load = Double.parseDouble(mLoadField.getText());
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Static(mHandle, mMotorSimPanel.getMotorConfig(),
                new StaticLoadMotorSimulationConfig(load));
    }

    @Override
    protected void updateSimulatorParams()
    {
        StaticLoadMotorSimulationConfig config = DataAccessorFactory.getInstance().getSpeedControllerAccessor().getMotorSimStaticModelConfig(mHandle);
        mLoadField.setText(Double.toString(config.mLoad));
    }
}

class RotationalLoadSimConfigPanel extends MotorSimWithModelPanel
{
    public RotationalLoadSimConfigPanel(int aKey)
    {
        super(aKey);
    }

    @Override
    public void submit()
    {
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Rotational(mHandle, mMotorSimPanel.getMotorConfig(),
                new RotationalLoadMotorSimulationConfig(0, 0));
    }

    @Override
    protected void updateSimulatorParams()
    {
        // Nothing to do
    }
}

class GravitationalLoadSimConfigPanel extends MotorSimWithModelPanel
{
    protected JTextField mLoadField;

    public GravitationalLoadSimConfigPanel(int aKey)
    {
        super(aKey);

        mLoadField = new JTextField(5);

        JPanel simPanel = new JPanel();
        simPanel.setBorder(new TitledBorder("Simulator Parameters"));
        simPanel.add(new JLabel("Load"));
        simPanel.add(mLoadField);

        add(simPanel, BorderLayout.NORTH);
    }

    @Override
    public void submit()
    {
        double load = Double.parseDouble(mLoadField.getText());
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().setSpeedControllerModel_Gravitational(mHandle, mMotorSimPanel.getMotorConfig(),
                new GravityLoadMotorSimulationConfig(load));
    }

    @Override
    protected void updateSimulatorParams()
    {
        GravityLoadMotorSimulationConfig config = DataAccessorFactory.getInstance().getSpeedControllerAccessor()
                .getMotorSimGravitationalModelConfig(mHandle);
        mLoadField.setText(Double.toString(config.getLoad()));
    }
}
