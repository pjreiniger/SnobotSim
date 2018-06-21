package com.snobot.simulator.gui.module_widget.settings;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.snobot.simulator.gui.motor_display.MotorCurveDisplay;
import com.snobot.simulator.motor_sim.DcMotorModelConfig;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import net.miginfocom.swing.MigLayout;

public class DcMotorConfigPanel extends JPanel
{
    private static final Logger sLOGGER = LogManager.getLogger(DcMotorConfigPanel.class);

    private JComboBox<String> mMotorSelectionBox;
    private JSpinner mNumMotors;
    private JTextField mGearReduction;
    private JTextField mGearboxEfficiency;

    private JTextField mMotorParams_NominalVoltage;
    private JTextField mMotorParams_FreeSpeedRpm;
    private JTextField mMotorParams_FreeCurrent;
    private JTextField mMotorParams_StallTorque;
    private JTextField mMotorParams_StallCurrent;
    private JCheckBox mMotorParams_Inverted;
    private JCheckBox mMotorParams_Brake;

    private MotorCurveDisplay mMotorCurveDisplay;

    private final DecimalFormat mDecimalFormat;
    private boolean mUpdatingMotorParams;
    private JLabel lblMotorType;
    private JPanel panel;
    private JPanel panel_1;

    public DcMotorConfigPanel()
    {
        initComponents();

        mNumMotors.setValue(1);
        mGearReduction.setText("1.0"); // NOPMD
        mGearboxEfficiency.setText("1.0"); // NOPMD
        setLayout(new MigLayout("", "[65px][4px][172px][316px,grow]", "[25px][6px][98px][6px][203px,grow]"));
        add(lblMotorType, "cell 0 0,alignx right,aligny center");
        add(mMotorSelectionBox, "cell 2 0,growx,aligny bottom");
        add(panel, "cell 0 2 3 1,grow");
        add(panel_1, "cell 0 4 3 1,growx,aligny top");
        add(mMotorCurveDisplay, "cell 3 0 1 5,grow");

        mDecimalFormat = new DecimalFormat("0.000");

        UpdateMotorConfigListener updateListener = new UpdateMotorConfigListener();

        mMotorSelectionBox.addItemListener(updateListener);
        mNumMotors.addChangeListener(updateListener);
        mGearReduction.addFocusListener(updateListener);
        mGearboxEfficiency.addFocusListener(updateListener);

        mUpdatingMotorParams = false;
    }

    public void setModelConfig(DcMotorModelConfig aConfig)
    {
        if (!mUpdatingMotorParams)
        {
            mUpdatingMotorParams = true;
            if (aConfig == null)
            {
                mNumMotors.setValue(1);
                mGearReduction.setText("1.0"); // NOPMD
                mGearboxEfficiency.setText("1.0"); // NOPMD
            }
            else
            {
                mMotorSelectionBox.setSelectedItem(aConfig.mFactoryParams.mMotorType);
                mNumMotors.setValue(aConfig.mFactoryParams.mNumMotors);
                mGearReduction.setText(Double.toString(aConfig.mFactoryParams.mGearReduction));
                mGearboxEfficiency.setText(Double.toString(aConfig.mFactoryParams.mGearboxEfficiency));

                mMotorParams_NominalVoltage.setText(mDecimalFormat.format(aConfig.mMotorParams.mNominalVoltage));
                mMotorParams_FreeSpeedRpm.setText(mDecimalFormat.format(aConfig.mMotorParams.mFreeSpeedRpm));
                mMotorParams_FreeCurrent.setText(mDecimalFormat.format(aConfig.mMotorParams.mFreeCurrent));
                mMotorParams_StallTorque.setText(mDecimalFormat.format(aConfig.mMotorParams.mStallTorque));
                mMotorParams_StallCurrent.setText(mDecimalFormat.format(aConfig.mMotorParams.mStallCurrent));
                mMotorParams_Inverted.setSelected(aConfig.mFactoryParams.ismInverted());
                mMotorParams_Brake.setSelected(aConfig.mFactoryParams.ismHasBrake());

                mMotorCurveDisplay.setCurveParams(aConfig);
            }
            mUpdatingMotorParams = false;
        }

        enableMotorParams(aConfig == null);


        // SwingUtilities.invokeLater(new Runnable()
        // {
        //
        // @Override
        // public void run()
        // {
        // repaint();
        // }
        // });
    }

    private void updateMotorConfig()
    {
        setModelConfig(getMotorConfig());
    }

    private void enableMotorParams(boolean aEnable)
    {
        mMotorParams_NominalVoltage.setEnabled(aEnable);
        mMotorParams_FreeSpeedRpm.setEnabled(aEnable);
        mMotorParams_FreeCurrent.setEnabled(aEnable);
        mMotorParams_StallTorque.setEnabled(aEnable);
        mMotorParams_StallCurrent.setEnabled(aEnable);
    }

    public DcMotorModelConfig getMotorConfig()
    {
        DcMotorModelConfig output = null;

        try
        {
            String selectedMotor = mMotorSelectionBox.getSelectedItem().toString();
            int numMotors = (Integer) (mNumMotors.getValue());
            double gearReduction = Double.parseDouble(mGearReduction.getText());
            double efficiency = Double.parseDouble(mGearboxEfficiency.getText());

            output = DataAccessorFactory.getInstance().getSimulatorDataAccessor().createMotor(selectedMotor, numMotors, gearReduction, efficiency,
                    mMotorParams_Inverted.isSelected(), mMotorParams_Brake.isSelected());
        }
        catch (NumberFormatException e)
        {
            sLOGGER.log(Level.ERROR, e);
        }

        return output;
    }

    private void initComponents()
    {
        setBorder(new TitledBorder(null, "Motor Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        mMotorSelectionBox = new JComboBox<>();
        mMotorSelectionBox.addItem("CIM");
        mMotorSelectionBox.addItem("Mini CIM");
        mMotorSelectionBox.addItem("Bag");
        mMotorSelectionBox.addItem("775 Pro");
        mMotorSelectionBox.addItem("Andymark RS 775-125");
        mMotorSelectionBox.addItem("Banebots RS 775");
        mMotorSelectionBox.addItem("Andymark 9015");
        mMotorSelectionBox.addItem("Banebots RS 550");

        lblMotorType = new JLabel("Motor Type");

        panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Transmission", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "Motor Parameters", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        mMotorCurveDisplay = new MotorCurveDisplay();

        JLabel lblNominalVoltage = new JLabel("Nominal Voltage");

        mMotorParams_NominalVoltage = new JTextField();
        mMotorParams_NominalVoltage.setColumns(10);

        JLabel lblFreeSpeedRpm = new JLabel("Free Speed RPM");

        mMotorParams_FreeSpeedRpm = new JTextField();
        mMotorParams_FreeSpeedRpm.setColumns(10);

        JLabel lblFreeCurrent = new JLabel("Free Current");

        mMotorParams_FreeCurrent = new JTextField();
        mMotorParams_FreeCurrent.setColumns(10);

        JLabel lblStallTorque = new JLabel("Stall Torque");

        mMotorParams_StallTorque = new JTextField();
        mMotorParams_StallTorque.setColumns(10);

        JLabel lblNewLabel = new JLabel("Stall Current");

        mMotorParams_StallCurrent = new JTextField();
        mMotorParams_StallCurrent.setColumns(10);

        JLabel lblInverted = new JLabel("Inverted");

        JLabel lblBrake = new JLabel("Brake");

        mMotorParams_Inverted = new JCheckBox("");

        mMotorParams_Brake = new JCheckBox("");
        panel_1.setLayout(new MigLayout("", "[79px][141px]", "[21px][21px][20px][20px][20px][20px][20px]"));
        panel_1.add(mMotorParams_Inverted, "cell 1 0,alignx center,aligny top");
        panel_1.add(mMotorParams_Brake, "cell 1 1,alignx center,aligny top");
        panel_1.add(lblInverted, "cell 0 0,alignx left,aligny center");
        panel_1.add(lblBrake, "cell 0 1,alignx left,aligny center");
        panel_1.add(lblNominalVoltage, "cell 0 2,alignx left,aligny center");
        panel_1.add(mMotorParams_NominalVoltage, "cell 1 2,growx,aligny top");
        panel_1.add(lblFreeSpeedRpm, "cell 0 3,alignx left,aligny center");
        panel_1.add(mMotorParams_FreeSpeedRpm, "cell 1 3,growx,aligny top");
        panel_1.add(lblFreeCurrent, "cell 0 4,alignx left,aligny center");
        panel_1.add(mMotorParams_FreeCurrent, "cell 1 4,growx,aligny top");
        panel_1.add(lblStallTorque, "cell 0 5,alignx left,aligny center");
        panel_1.add(mMotorParams_StallTorque, "cell 1 5,growx,aligny top");
        panel_1.add(lblNewLabel, "cell 0 6,alignx left,aligny center");
        panel_1.add(mMotorParams_StallCurrent, "cell 1 6,growx,aligny top");

        JLabel lblNumMotors = new JLabel("Num Motors");
        mNumMotors = new JSpinner();

        JLabel lblGearRatio = new JLabel("Gear Ratio");
        mGearReduction = new JTextField();

        JLabel lblEfficiency = new JLabel("Efficiency");
        mGearboxEfficiency = new JTextField();
        panel.setLayout(new MigLayout("", "[57px][162px]", "[20px][20px][20px]"));
        panel.add(lblNumMotors, "cell 0 0,alignx left,aligny top");
        panel.add(lblGearRatio, "cell 0 1,alignx left,aligny center");
        panel.add(lblEfficiency, "cell 0 2,alignx left,aligny center");
        panel.add(mGearboxEfficiency, "cell 1 2,growx,aligny top");
        panel.add(mGearReduction, "cell 1 1,growx,aligny top");
        panel.add(mNumMotors, "cell 1 0,growx,aligny top");
    }

    private class UpdateMotorConfigListener implements ItemListener, ChangeListener, FocusListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                updateMotorConfig();
            }
        }

        @Override
        public void stateChanged(ChangeEvent e)
        {
            updateMotorConfig();
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            updateMotorConfig();
        }

        @Override
        public void focusGained(FocusEvent e)
        {
            // Nothing to do
        }
    }
}
