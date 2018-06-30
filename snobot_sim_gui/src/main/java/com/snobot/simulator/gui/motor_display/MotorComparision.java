package com.snobot.simulator.gui.motor_display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class MotorComparision extends JPanel
{
    public void plotSeries(XYSeriesCollection aSeries)
    {

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Motion Profile",
                "Time (sec)",
                "Data",
                aSeries,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));

        setLayout(new BorderLayout());
        add(chartPanel);
    }

    public static Map<String, XYSeries> getSeries(String aPrefix, String aFilename) throws IOException
    {
        Map<String, XYSeries> output = new HashMap<>();

        XYSeries positionSeries = new XYSeries(aPrefix + "Position");
        XYSeries velocitySeries = new XYSeries(aPrefix + "Velocity");
        XYSeries currentSeries = new XYSeries(aPrefix + "Current");

        output.put("Position", positionSeries);
        output.put("Velocity", velocitySeries);
        output.put("Current", currentSeries);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(aFilename), "UTF-8")))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");

                double dt = Double.parseDouble(parts[0]);
                double pos = Double.parseDouble(parts[1]);
                double vel = Double.parseDouble(parts[2]);
                double cur = Double.parseDouble(parts[3]);

                positionSeries.add(dt, pos);
                velocitySeries.add(dt, vel);
                currentSeries.add(dt, cur);
            }
        }

        return output;
    }
}
