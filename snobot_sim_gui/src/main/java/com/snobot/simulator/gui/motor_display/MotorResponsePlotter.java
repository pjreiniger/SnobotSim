// package com.snobot.simulator.gui.motor_display;
//
// import java.awt.BorderLayout;
// import java.awt.Dimension;
//
// import javax.swing.JFrame;
// import javax.swing.JPanel;
//
// import org.jfree.chart.ChartFactory;
// import org.jfree.chart.ChartPanel;
// import org.jfree.chart.JFreeChart;
// import org.jfree.chart.plot.PlotOrientation;
// import org.jfree.data.xy.XYSeries;
// import org.jfree.data.xy.XYSeriesCollection;
//
// import com.snobot.simulator.motor_sim.DcMotorModel;
// import com.snobot.simulator.motor_sim.DcMotorModelConfig;
// import com.snobot.simulator.motor_sim.GravityLoadDcMotorSim;
// import com.snobot.simulator.module_wrapper.interfaces.IMotorSimulator;
// import com.snobot.simulator.motor_sim.motors.MakeTransmission;
// import com.snobot.simulator.motor_sim.motors.VexMotorFactory;
//
// public class MotorResponsePlotter extends JPanel
// {
//
// public MotorResponsePlotter()
// {
// XYSeriesCollection series = new XYSeriesCollection();
//
// XYSeries accelerationPoints = new XYSeries("Acceleration");
// XYSeries velocityPoints = new XYSeries("Velocity");
// XYSeries positionPoints = new XYSeries("Position");
//
// // series.addSeries(accelerationPoints);
// series.addSeries(velocityPoints);
// // series.addSeries(positionPoints);
//
// double gearReduction = 10;
// double load = .01;
//
// double spinUpVoltage = 1;
// double spinDownVoltage = 0;
//
// double spinUpTime = 30;
// double spinDownTime = 30;
// double dt = .002;
//
// // DcMotorModel rawMotor = VexMotorFactory.makeCIMMotor();
// DcMotorModelConfig rawMotor = VexMotorFactory.makeBaneBotsRS775();
// DcMotorModelConfig transMotor = MakeTransmission.makeTransmission(rawMotor,
// 1, gearReduction, 1);
//
// // IMotorSimulator motorSim = new StaticLoadDcMotorSim(transMotor,
// // load);
// IMotorSimulator motorSim = new GravityLoadDcMotorSim(transMotor, load);
//
// double t = 0;
// for (; t < spinUpTime; t += dt)
// {
// motorSim.setVoltagePercentage(spinUpVoltage);
// motorSim.update(dt);
//
// positionPoints.add(t, motorSim.getPosition());
// velocityPoints.add(t, motorSim.getVelocity());
// accelerationPoints.add(t, motorSim.getAcceleration());
// }
//
// for (; t < (spinDownTime + spinUpTime); t += dt)
// {
// motorSim.setVoltagePercentage(spinDownVoltage);
// motorSim.update(dt);
//
// positionPoints.add(t, motorSim.getPosition());
// velocityPoints.add(t, motorSim.getVelocity());
// accelerationPoints.add(t, motorSim.getAcceleration());
// }
//
//
// final JFreeChart chart = ChartFactory.createXYLineChart(
// "Motor Response",
// "Time (sec)",
// "Data",
// series,
// PlotOrientation.VERTICAL, true,
// true,
// false);
//
// ChartPanel chartPanel = new ChartPanel(chart);
// chartPanel.setPreferredSize(new Dimension(400, 300));
//
// setLayout(new BorderLayout());
// add(chartPanel);
// }
//
// public static void main(String[] args)
// {
// MotorResponsePlotter panel = new MotorResponsePlotter();
//
// JFrame frame = new JFrame();
// frame.add(panel);
// frame.pack();
// frame.setVisible(true);
// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// }
//
// }
