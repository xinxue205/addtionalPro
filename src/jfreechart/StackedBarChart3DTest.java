package jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleEdge;

public class StackedBarChart3DTest {
	public static void main(String[] args) throws IOException {
		double[][] data = {{1,2,3,4},{2,3,4,5},{3,4,5,6}};
		String[] rowsKeys={"桃","桔","梨"};
		String[] colsKeys={"上海","长沙","广州","北京"};
		CategoryDataset cd = DatasetUtilities.createCategoryDataset(rowsKeys,colsKeys,data);
		/*DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("在行自助银行", Double.valueOf(2));
		dataset.setValue("在行服务点", Double.valueOf(3));
		dataset.setValue("在行智能银行", Double.valueOf(4));
		dataset.setValue("离行自助银行", Double.valueOf(5));
		dataset.setValue("离行服务点", Double.valueOf(6));
		dataset.setValue("离行智能银行", Double.valueOf(11));*/
		
		//通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createStackedBarChart3D("水果图", null, null, cd, PlotOrientation.VERTICAL, true, true, false);
		//设置chart属性
		chart.setBorderVisible(false);
		chart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,12)); 
		chart.getLegend().setPosition(RectangleEdge.RIGHT);
		chart.getTitle().setFont(new Font("宋体", Font.PLAIN, 20));
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getDomainAxis().setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
		StackedBarRenderer3D renderer = new StackedBarRenderer3D();
		renderer.setBasePaint(Color.WHITE);
		plot.setRenderer(renderer);
		
		/*	
		PiePlot pieplot = (PiePlot) chart.getPlot();
		//设置饼图属性
		pieplot.setStartAngle(150D);
		pieplot.setOutlineVisible(false);
		pieplot.setLabelFont(new Font("宋体", 0, 12));
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setBackgroundPaint(Color.WHITE); */ 
		
		File file = new File("d:/StackedBarChart3Dtemp.png");
		ChartUtilities.saveChartAsPNG(file, chart, 600, 400);
	}
	
}
