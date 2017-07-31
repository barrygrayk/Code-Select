/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MenuView;

/**
 * 
 * @author Barry Gray
 */
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.chartistjsf.model.chart.AspectRatio;
import org.chartistjsf.model.chart.Axis;
import org.chartistjsf.model.chart.AxisType;
import org.chartistjsf.model.chart.ChartSeries;
import org.chartistjsf.model.chart.LineChartModel;
import org.chartistjsf.model.chart.LineChartSeries;
import org.chartistjsf.model.chart.PieChartModel;
import org.primefaces.event.ItemSelectEvent;

@ManagedBean
public class ChartDataBean {

	private LineChartModel lineModel;
        private PieChartModel pieChartModel;

	public ChartDataBean() {
		createLineModel();
                createPieChart();

	}

	public void createLineModel() {
		Random random = new Random();
		lineModel = new LineChartModel();
		lineModel.setAspectRatio(AspectRatio.GOLDEN_SECTION);

		lineModel.addLabel("1");
		lineModel.addLabel("2");
		lineModel.addLabel("3");
		lineModel.addLabel("4");
		lineModel.addLabel("5");
		lineModel.addLabel("6");
		lineModel.addLabel("7");
		lineModel.addLabel("8");

		LineChartSeries series1 = new LineChartSeries();
		series1.setName("Series 1");

		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));
		series1.set(random.nextInt(10));

		LineChartSeries series2 = new LineChartSeries();
		series2.setName("Series 2");

		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));
		series2.set(random.nextInt(10));

		LineChartSeries series3 = new LineChartSeries();
		series3.setName("Series 3");

		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));
		series3.set(random.nextInt(10));

		Axis xAxis = lineModel.getAxis(AxisType.X);
		lineModel.addSeries(series1);
		lineModel.addSeries(series2);
		lineModel.addSeries(series3);

		lineModel.setAnimateAdvanced(true);
		lineModel.setShowTooltip(true);
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
				+ ((ChartSeries) lineModel.getSeries().get(event.getSeriesIndex())).getData().get(event.getItemIndex())
				+ ", Series name:" + ((ChartSeries) lineModel.getSeries().get(event.getSeriesIndex())).getName());

		FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
	}
	
		/**
	 * @return the lineModel
	 */
	public LineChartModel getLineModel() {
		return lineModel;
	}

	/**
	 * @param lineModel
	 *            the lineModel to set
	 */
	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}
        
        
	public void createPieChart() {
		pieChartModel = new PieChartModel();

		pieChartModel.addLabel("Bananas");
		pieChartModel.addLabel("Apples");
		pieChartModel.addLabel("Grapes");

		pieChartModel.set(20);
		pieChartModel.set(10);
		pieChartModel.set(30);

		pieChartModel.setShowTooltip(true);
	}

	public void pieItemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
				+ pieChartModel.getData().get(event.getItemIndex()));

		FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
	}
	
		/**
	 * @return the pieChartModel
	 */
	public PieChartModel getPieChartModel() {
		return pieChartModel;
	}

	/**
	 * @param pieChartModel
	 *            the pieChartModel to set
	 */
	public void setPieChartModel(PieChartModel pieChartModel) {
		this.pieChartModel = pieChartModel;
	}
 

}