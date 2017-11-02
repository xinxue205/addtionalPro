package jfreechart;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleEdge;

public class ClusteredXYBarChartTest {
	public static void main(String[] args) throws IOException {
		double[][] data = {{1,2,3,4},{2,3,4,5},{3,4,5,6}};
		String[] rowsKeys={"��","��","��"};
		String[] colsKeys={"�Ϻ�","��ɳ","����","����"};
		CategoryDataset cd = DatasetUtilities.createCategoryDataset(rowsKeys,colsKeys,data);
		/*DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("������������", Double.valueOf(2));
		dataset.setValue("���з����", Double.valueOf(3));
		dataset.setValue("������������", Double.valueOf(4));
		dataset.setValue("������������", Double.valueOf(5));
		dataset.setValue("���з����", Double.valueOf(6));
		dataset.setValue("������������", Double.valueOf(11));*/
		
		//ͨ�������JFreeChart����
		JFreeChart chart = ChartFactory.createStackedBarChart3D("ˮ��ͼ", null, null, cd, PlotOrientation.VERTICAL, true, true, false);
		//����chart����
		chart.setBorderVisible(false);
		chart.getLegend().setItemFont(new Font("����",Font.PLAIN,12)); 
		chart.getLegend().setPosition(RectangleEdge.RIGHT);
		chart.getTitle().setFont(new Font("����", Font.PLAIN, 20));
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getDomainAxis().setTickLabelFont(new Font("����", Font.PLAIN, 12));
		ClusteredXYBarRenderer renderer = new ClusteredXYBarRenderer();
		plot.setRenderer((CategoryItemRenderer) renderer);
		
		/*	
		PiePlot pieplot = (PiePlot) chart.getPlot();
		//���ñ�ͼ����
		pieplot.setStartAngle(150D);
		pieplot.setOutlineVisible(false);
		pieplot.setLabelFont(new Font("����", 0, 12));
		pieplot.setNoDataMessage("�������ʾ");
		pieplot.setBackgroundPaint(Color.WHITE); */ 
		
		File file = new File("d:/StackedBarChart3Dtemp.png");
		ChartUtilities.saveChartAsPNG(file, chart, 600, 400);
	}
	
}
