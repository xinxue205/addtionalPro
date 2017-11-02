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
		dataset.addValue(300, "����", "ƻ��");
		dataset.addValue(200, "����", "����");
		dataset.addValue(500, "����", "����");
		dataset.addValue(340, "����", "â��");
		dataset.addValue(280, "����", "��֦");
		
		//ͨ������������JFreeChart����
		JFreeChart chart = ChartFactory.createBarChart3D("ˮ������ͳ��ͼ", "ˮ��","����",	dataset,PlotOrientation.VERTICAL,false,false,false);
		//����chart����
		chart.setBorderVisible(false);
		chart.getTitle().setFont(new Font("����", Font.PLAIN, 20));
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.getRangeAxis().setLabelFont(new Font("����", Font.PLAIN, 12));
		plot.getDomainAxis().setTickLabelFont(new Font("����", Font.PLAIN, 12));
		plot.getDomainAxis().setLabelFont(new Font("����", Font.PLAIN, 12));
		/*	
		PiePlot pieplot = (PiePlot) chart.getPlot();
		//���ñ�ͼ����
		pieplot.setStartAngle(150D);
		pieplot.setOutlineVisible(false);
		pieplot.setLabelFont(new Font("����", 0, 12));
		pieplot.setNoDataMessage("��������ʾ");
		pieplot.setBackgroundPaint(Color.WHITE); */ 
		
		File file = new File("d:/BarChart3Dtemp.png");
		ChartUtilities.saveChartAsPNG(file, chart, 600, 400);
	}
	
}
