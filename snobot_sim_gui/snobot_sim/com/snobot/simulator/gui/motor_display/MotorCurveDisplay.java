package com.snobot.simulator.gui.motor_display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.snobot.simulator.motor_sim.DcMotorModelConfig;


public class MotorCurveDisplay extends JPanel
{
    private final ValueMarker mRpmMarker;

    protected final XYSeries mCurrentPoints;
    protected final XYSeries mTorquePoints;
    protected final XYSeries mPowerPoints;
    protected final XYSeries mEfficiencyPoints;
    protected final JFreeChart mChart;

    protected final int mNumPoints;

    public MotorCurveDisplay()
    {
        mNumPoints = 600;

        mCurrentPoints = new XYSeries("Current");
        mTorquePoints = new XYSeries("Torque");
        mPowerPoints = new XYSeries("Power");
        mEfficiencyPoints = new XYSeries("Efficiency");

        XYSeriesCollection series = new XYSeriesCollection();
        series.addSeries(mCurrentPoints);
        series.addSeries(mTorquePoints);
        series.addSeries(mPowerPoints);
        series.addSeries(mEfficiencyPoints);

        mChart = ChartFactory.createXYLineChart(
                "Unknown Motor",
                "RPM",
                "Data",
                series,
                PlotOrientation.VERTICAL, true,
                true,
                false);


        mRpmMarker = new ValueMarker(0);
        mRpmMarker.setPaint(Color.black);

        XYPlot plot = (XYPlot) mChart.getPlot();
        plot.addDomainMarker(mRpmMarker);

        ChartPanel chartPanel = new ChartPanel(mChart);
        chartPanel.setPreferredSize(new Dimension(400, 300));

        setLayout(new BorderLayout());
        add(chartPanel);
    }

    public void setCurveParams(DcMotorModelConfig aModel)
    {
        setCurveParams(aModel.mFactoryParams.mMotorType, aModel.mMotorParams.mNominalVoltage, aModel.mMotorParams.mFreeSpeedRpm,
                aModel.mMotorParams.mStallCurrent, aModel.mMotorParams.mFreeCurrent, aModel.mMotorParams.mStallTorque);
    }

    public void setCurveParams(String aMotorName, double aNominalVoltage, double aFreeSpeedRpm, double aStallCurrent, double aFreeCurrent, double aStallTorque)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                mChart.setTitle(aMotorName);

                mCurrentPoints.clear();
                mTorquePoints.clear();
                mPowerPoints.clear();
                mEfficiencyPoints.clear();


                double currentSlope = (aFreeCurrent - aStallCurrent) / aFreeSpeedRpm;
                double torqueSlope = (0 - aStallTorque) / aFreeSpeedRpm;

                int dRpm = (int) Math.ceil(aFreeSpeedRpm / mNumPoints);

                for (int rpm = 0; rpm < aFreeSpeedRpm; rpm += dRpm)
                {
                    addPoint(aNominalVoltage, aStallCurrent, aStallTorque, rpm, currentSlope, torqueSlope);
                }

                // Add the last point always
                addPoint(aNominalVoltage, aStallCurrent, aStallTorque, (int) aFreeSpeedRpm, currentSlope, torqueSlope);
            }
        });
    }

    private void addPoint(
            double aNominalVoltage, double aStallCurrent, double aStallTorque,
            int aRpm, double aCurrentSlope, double aTorqueSlope)
    {
        final double omega = 2 * aRpm * Math.PI / 60;
        final double current = aStallCurrent + aRpm * aCurrentSlope;
        final double torque = aStallTorque + aRpm * aTorqueSlope;
        final double inputPower = aNominalVoltage * current;
        final double outputPower = torque * omega;
        final double efficiency = outputPower / inputPower * 100;

        mCurrentPoints.add(aRpm, current);
        mTorquePoints.add(aRpm, torque);
        mPowerPoints.add(aRpm, outputPower);
        mEfficiencyPoints.add(aRpm, efficiency);
    }

    public void setCurrentRpm(double aRpm)
    {
        mRpmMarker.setPaint(Color.black);
        mRpmMarker.setValue(aRpm);
    }

}
