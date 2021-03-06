/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.test.domain.chart.builder;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import ar.com.fdvs.dj.domain.DJHyperLink;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.DJChartOptions;
import ar.com.fdvs.dj.domain.chart.builder.DJStackedAreaChartBuilder;
import ar.com.fdvs.dj.domain.chart.plot.DJAxisFormat;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import ar.com.fdvs.dj.domain.hyperlink.LiteralExpression;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import net.sf.jasperreports.charts.design.JRDesignAreaPlot;
import net.sf.jasperreports.charts.design.JRDesignCategoryDataset;
import net.sf.jasperreports.charts.type.EdgeEnum;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.LineStyleEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import net.sf.jasperreports.view.JasperViewer;

public class StackedAreaChartBuilderTest extends BaseDjReportTest {
	private DynamicReportBuilder drb; 
	private JRDesignChart chart;
	
	protected void setUp() throws Exception {
        super.setUp();
        drb = new DynamicReportBuilder();

		Style headerStyle = new Style();
		headerStyle.setFont(Font.VERDANA_MEDIUM_BOLD);
		headerStyle.setBorderBottom(Border.PEN_2_POINT());
		headerStyle.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
		headerStyle.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
		headerStyle.setBackgroundColor(Color.DARK_GRAY);
		headerStyle.setTextColor(Color.WHITE);
		headerStyle.setTransparency(ModeEnum.OPAQUE);
		drb.setDefaultStyles(null, null, headerStyle, null);			
		
		AbstractColumn columnState = ColumnBuilder.getNew()
		.setColumnProperty("state", String.class.getName()).setTitle(
				"State").setWidth(new Integer(85)).build();
		AbstractColumn columnBranch = ColumnBuilder.getNew()
		.setColumnProperty("branch", String.class.getName()).setTitle(
				"Branch").setWidth(new Integer(85)).build();
		AbstractColumn columnaQuantity = ColumnBuilder.getNew()
		.setColumnProperty("quantity", Long.class.getName()).setTitle(
				"Quantity").setWidth(new Integer(80)).build();
		AbstractColumn columnAmount = ColumnBuilder.getNew()
		.setColumnProperty("amount", Float.class.getName()).setTitle(
				"Amount").setWidth(new Integer(90)).build();

		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);
		
		GroupBuilder gb1 = new GroupBuilder();
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnState)
				.addFooterVariable(columnAmount,CalculationEnum.SUM)
			.addFooterVariable(columnaQuantity,CalculationEnum.SUM) 
			.addVariable("group_state_name", columnState, CalculationEnum.FIRST)
			.setGroupLayout(GroupLayout.DEFAULT) 
			.build();		
/*		GroupBuilder gb2 = new GroupBuilder(); 
  		DJGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnBranch)
				.setGroupLayout(GroupLayout.VALUE_FOR_EACH) 
				.build();*/
		
		drb.addGroup(g1);
		//drb.addGroup(g2);
		
		drb.setUseFullPageWidth(true);
		
		DJAxisFormat categoryAxisFormat = new DJAxisFormat("category");
		categoryAxisFormat.setLabelFont(Font.ARIAL_SMALL);
		categoryAxisFormat.setLabelColor(Color.DARK_GRAY);
		categoryAxisFormat.setTickLabelFont(Font.ARIAL_SMALL);
		categoryAxisFormat.setTickLabelColor(Color.DARK_GRAY);
		categoryAxisFormat.setTickLabelMask("");
		categoryAxisFormat.setLineColor(Color.DARK_GRAY);
		
		DJAxisFormat valueAxisFormat = new DJAxisFormat("value");
		valueAxisFormat.setLabelFont(Font.ARIAL_SMALL);
		valueAxisFormat.setLabelColor(Color.DARK_GRAY);
		valueAxisFormat.setTickLabelFont(Font.ARIAL_SMALL);
		valueAxisFormat.setTickLabelColor(Color.DARK_GRAY);
		valueAxisFormat.setTickLabelMask("#,##0.0");
		valueAxisFormat.setLineColor(Color.DARK_GRAY);
		
		DJChart djChart = new DJStackedAreaChartBuilder()
		//chart		
		.setX(20)
		.setY(10)
		.setWidth(500)
		.setHeight(250)
		.setCentered(false)
		.setBackColor(Color.LIGHT_GRAY)
		.setShowLegend(true)
		.setPosition(DJChartOptions.POSITION_FOOTER)
		.setTitle(new StringExpression() {			
			public Object evaluate(Map fields, Map variables, Map parameters) {
				return variables.get("group_state_name");
			}
		})
		.setTitleColor(Color.DARK_GRAY)
		.setTitleFont(Font.ARIAL_BIG_BOLD)
		.setSubtitle("subtitle")
		.setSubtitleColor(Color.DARK_GRAY)
		.setSubtitleFont(Font.COURIER_NEW_BIG_BOLD)
		.setLegendColor(Color.DARK_GRAY)
		.setLegendFont(Font.COURIER_NEW_MEDIUM_BOLD)
		.setLegendBackgroundColor(Color.WHITE)
		.setLegendPosition(EdgeEnum.BOTTOM)
		.setTitlePosition(EdgeEnum.TOP)
		.setLineStyle(LineStyleEnum.DOTTED)
		.setLineWidth(1)
		.setLineColor(Color.DARK_GRAY)
		.setPadding(5)
		//dataset
		.setCategory((PropertyColumn) columnBranch)
		.addSerie(columnaQuantity, "quant.")
		.addSerie(columnAmount)
		//plot
		.setCategoryAxisFormat(categoryAxisFormat)
		.setValueAxisFormat(valueAxisFormat)
		.build();
		drb.addChart(djChart);
		
		DJHyperLink djlink = new DJHyperLink();
		djlink.setExpression(new StringExpression() {
			public Object evaluate(Map fields, Map variables, Map parameters) {				
				return "http://thisIsAURL?count=" + variables.get("REPORT_COUNT");
			}
		});
		djlink.setTooltip(new LiteralExpression("I'm a literal tootltip"));		
		djChart.setLink(djlink);
		
		Map<AbstractColumn, JRDesignVariable> vars = new HashMap<AbstractColumn, JRDesignVariable>();
		vars.put(columnaQuantity, new JRDesignVariable());
		vars.put(columnAmount, new JRDesignVariable());
		JRDesignGroup group = new JRDesignGroup();
		chart = djChart.transform(new DynamicJasperDesign(), "", group, group, vars, 0);
	}
	
	public void testChart() {
		assertEquals(20, chart.getX());
		assertEquals(10, chart.getY());
		assertEquals(500, chart.getWidth());
		assertEquals(250, chart.getHeight());
		assertEquals(Color.LIGHT_GRAY, chart.getBackcolor());
		assertEquals(Boolean.TRUE, chart.getShowLegend());
		assertNotNull(chart.getTitleExpression().getText());
		assertEquals(Color.DARK_GRAY, chart.getTitleColor());
		testFont(Font.ARIAL_BIG_BOLD, chart.getTitleFont());		
		assertNotNull(chart.getSubtitleExpression().getText());
		assertEquals(Color.DARK_GRAY, chart.getSubtitleColor());
		testFont(Font.COURIER_NEW_BIG_BOLD, chart.getSubtitleFont());
		assertEquals(Color.DARK_GRAY, chart.getLegendColor());
		testFont(Font.COURIER_NEW_MEDIUM_BOLD, chart.getLegendFont());
		assertEquals(Color.WHITE, chart.getLegendBackgroundColor());
		assertEquals(EdgeEnum.BOTTOM, chart.getLegendPositionValue() );
		assertEquals(EdgeEnum.TOP, chart.getTitlePositionValue());
        assertEquals(LineStyleEnum.DOTTED, chart.getLineBox().getPen().getLineStyleValue());
		assertEquals(1f, chart.getLineBox().getPen().getLineWidth());
		assertEquals(Color.DARK_GRAY, chart.getLineBox().getPen().getLineColor());
		assertEquals(new Integer(5), chart.getLineBox().getPadding());
	}
	
	public void testDataset() {
		JRDesignCategoryDataset dataset = (JRDesignCategoryDataset) chart.getDataset();
		assertEquals(2, dataset.getSeriesList().size());
		assertNotNull(dataset.getSeries()[0].getLabelExpression().getText());
		assertNotNull(dataset.getSeries()[0].getSeriesExpression().getText());
	}
	
	public void testPlot() {
		JRDesignAreaPlot plot = (JRDesignAreaPlot) chart.getPlot();
		
		assertNotNull(plot.getCategoryAxisLabelExpression().getText());
		testFont(Font.ARIAL_SMALL, plot.getCategoryAxisLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getCategoryAxisLabelColor());		
		testFont(Font.ARIAL_SMALL, plot.getCategoryAxisTickLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getCategoryAxisTickLabelColor());
		assertEquals("", plot.getCategoryAxisTickLabelMask());
		assertEquals(Color.DARK_GRAY, plot.getCategoryAxisLineColor());
		
		assertNotNull(plot.getValueAxisLabelExpression().getText());
		testFont(Font.ARIAL_SMALL, plot.getValueAxisLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getValueAxisLabelColor());		
		testFont(Font.ARIAL_SMALL, plot.getValueAxisTickLabelFont());
		assertEquals(Color.DARK_GRAY, plot.getValueAxisTickLabelColor());
		assertEquals("#,##0.0", plot.getValueAxisTickLabelMask());
		assertEquals(Color.DARK_GRAY, plot.getValueAxisLineColor());
	}
	
	public DynamicReport buildReport() throws Exception {		
		return drb.build();
	}

	private void testFont(Font djFont, JRFont jrFont) {
		assertEquals(djFont.getFontName(), jrFont.getFontName());
		assertEquals(djFont.getFontSize(), jrFont.getFontsize());
		assertEquals(djFont.isBold(), jrFont.isBold());
		assertEquals(djFont.isItalic(), jrFont.isItalic());
	}
	
	public static void main(String[] args) throws Exception {
		StackedAreaChartBuilderTest test = new StackedAreaChartBuilderTest();
		test.setUp();
		test.testReport();
		JasperViewer.viewReport(test.jp);
	}
}
