package jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart3DTest {
	public static void main(String[] args) throws IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("在行自助银行", 22);
		dataset.setValue("在行单点", 11);
		dataset.setValue("在行智能银行", 23);
		dataset.setValue("离行自助银行", 34);
		dataset.setValue("离行单点", 23);
		dataset.setValue("离行智能银行", 53);

		//通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createPieChart3D("全行网点规模", dataset,true, false, false);

		//设置chart属性
		chart.setBorderVisible(false);
		chart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,12)); 
		chart.getTitle().setVisible(true);
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 20));  

		PiePlot pieplot = (PiePlot) chart.getPlot();
		//设置饼图属性
		//pieplot.setStartAngle(150D);
		pieplot.setOutlineVisible(false);
		pieplot.setLabelFont(new Font("宋体", 0, 12));
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setBackgroundPaint(Color.WHITE);  
		
		//pieplot.setDirection(Rotation.CLOCKWISE);
		pieplot.setShadowXOffset(15);
		pieplot.setShadowYOffset(25);
		/*	
		PiePlot pieplot = (PiePlot) chart.getPlot();
		//设置饼图属性
		pieplot.setStartAngle(150D);
		pieplot.setOutlineVisible(false);
		pieplot.setLabelFont(new Font("宋体", 0, 12));
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setBackgroundPaint(Color.WHITE); */ 
		
		File file = new File("d:/PieChart3Dtemp.png");
		ChartUtilities.saveChartAsPNG(file, chart, 600, 400);
	}
	
}
