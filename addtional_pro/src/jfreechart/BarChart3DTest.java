package jfreechart;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChart3DTest {
	public static void main(String[] args) throws IOException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(300, "广州", "苹果");
		dataset.addValue(200, "广州", "梨子");
		dataset.addValue(500, "广州", "葡萄");
		dataset.addValue(340, "广州", "芒果");
		dataset.addValue(280, "广州", "荔枝");
		
		//通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createBarChart3D("水果销量统计图", "水果","销量",	dataset,PlotOrientation.VERTICAL,false,false,false);
		//设置chart属性
		chart.setBorderVisible(false);
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 20));
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getRangeAxis().setLabelFont(new Font("宋体", Font.PLAIN, 12));
		plot.getDomainAxis().setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
		plot.getDomainAxis().setLabelFont(new Font("宋体", Font.PLAIN, 12));
		/*	
		PiePlot pieplot = (PiePlot) chart.getPlot();
		//设置饼图属性
		pieplot.setStartAngle(150D);
		pieplot.setOutlineVisible(false);
		pieplot.setLabelFont(new Font("宋体", 0, 12));
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setBackgroundPaint(Color.WHITE); */ 
		
		File file = new File("d:/BarChart3Dtemp.png");
		ChartUtilities.saveChartAsPNG(file, chart, 600, 400);
	}
	
}
