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
		dataset.setValue("������������", 22);
		dataset.setValue("���е���", 11);
		dataset.setValue("������������", 23);
		dataset.setValue("������������", 34);
		dataset.setValue("���е���", 23);
		dataset.setValue("������������", 53);

		//ͨ������������JFreeChart����
		JFreeChart chart = ChartFactory.createPieChart3D("ȫ�������ģ", dataset,true, false, false);

		//����chart����
		chart.setBorderVisible(false);
		chart.getLegend().setItemFont(new Font("����",Font.PLAIN,12)); 
		chart.getTitle().setVisible(true);
		chart.getTitle().setFont(new Font("����", Font.PLAIN, 20));  

		PiePlot pieplot = (PiePlot) chart.getPlot();
		//���ñ�ͼ����
		//pieplot.setStartAngle(150D);
		pieplot.setOutlineVisible(false);
		pieplot.setLabelFont(new Font("����", 0, 12));
		pieplot.setNoDataMessage("��������ʾ");
		pieplot.setBackgroundPaint(Color.WHITE);  
		
		//pieplot.setDirection(Rotation.CLOCKWISE);
		pieplot.setShadowXOffset(15);
		pieplot.setShadowYOffset(25);
		/*	
		PiePlot pieplot = (PiePlot) chart.getPlot();
		//���ñ�ͼ����
		pieplot.setStartAngle(150D);
		pieplot.setOutlineVisible(false);
		pieplot.setLabelFont(new Font("����", 0, 12));
		pieplot.setNoDataMessage("��������ʾ");
		pieplot.setBackgroundPaint(Color.WHITE); */ 
		
		File file = new File("d:/PieChart3Dtemp.png");
		ChartUtilities.saveChartAsPNG(file, chart, 600, 400);
	}
	
}
