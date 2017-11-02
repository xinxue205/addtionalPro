package jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

public class LineChartTest {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		double[][] data = {{18,25,31,24},{22,11,25,21},{35,19,32,16}};
		String[] rowsKeys={"桃","桔","梨"};
		String[] colsKeys={"上海","长沙","广州","北京"};
		CategoryDataset cd = DatasetUtilities.createCategoryDataset(rowsKeys,colsKeys,data);
		
		//通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createLineChart("水果图", null, null, cd, PlotOrientation.VERTICAL, true, true, false);
		//设置chart属性
		chart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,12)); 
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 20));
		chart.setBorderVisible(false);
		
		CategoryPlot plot=chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.getDomainAxis().setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
		plot.getDomainAxis().setLabelFont(new Font("宋体", Font.PLAIN, 12));
		plot.getRangeAxis().setMinorTickCount(15);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlineStroke(new BasicStroke(1));
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		plot.setOutlineVisible(false);
		
		LineAndShapeRenderer renderer=(LineAndShapeRenderer) plot.getRenderer();
		renderer.setShapesVisible(true);
		
		/*	CategoryPlot plot = chart.getCategoryPlot();
		plot.getDomainAxis().setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
		StackedBarRenderer3D renderer = new StackedBarRenderer3D();
		renderer.setBasePaint(Color.WHITE);
		plot.setRenderer(renderer);
		
		
		PiePlot pieplot = (PiePlot) chart.getPlot();
		//设置饼图属性
		pieplot.setStartAngle(150D);
		pieplot.setOutlineVisible(false);
		pieplot.setLabelFont(new Font("宋体", 0, 12));
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setBackgroundPaint(Color.WHITE); */ 
		
		File file = new File("d:/LineCharttemp.png");
		ChartUtilities.saveChartAsPNG(file, chart, 600, 400);
	}
	
}
