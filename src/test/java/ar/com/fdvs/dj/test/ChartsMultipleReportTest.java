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

package ar.com.fdvs.dj.test;

import java.awt.Color;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.DJChartOptions;
import ar.com.fdvs.dj.domain.chart.builder.DJBarChartBuilder;
import ar.com.fdvs.dj.domain.chart.builder.DJPieChartBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import net.sf.jasperreports.view.JasperViewer;

public class ChartsMultipleReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();
		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBorder(Border.PEN_2_POINT());
		headerStyle.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
		headerStyle.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);

		Style titleStyle = new Style();
		titleStyle.setFont(new Font(18, Font._FONT_VERDANA, true));
		Style importeStyle = new Style();
		importeStyle.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER());
		oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		oddRowStyle.setTransparency(ModeEnum.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		int margin = 20;
		drb
				.setTitleStyle(titleStyle)
				.setTitle("November " + getYear() +" sales report")					//defines the title of the report
				.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")
				.setDetailHeight(15).setLeftMargin(margin)
				.setMargins(margin, margin, margin, margin)
				.setPrintBackgroundOnOddRows(true)
				.setPrintColumnNames(false)
				.setOddRowBackgroundStyle(oddRowStyle);

		AbstractColumn columnState = ColumnBuilder.getNew()
				.setColumnProperty("state", String.class.getName()).setTitle(
						"State").setWidth(new Integer(85))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnBranch = ColumnBuilder.getNew()
				.setColumnProperty("branch", String.class.getName()).setTitle(
						"Branch").setWidth(new Integer(85)).setStyle(
						detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaProductLine = ColumnBuilder.getNew()
				.setColumnProperty("productLine", String.class.getName())
				.setTitle("Product Line").setWidth(new Integer(85)).setStyle(
						detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaItem = ColumnBuilder.getNew()
				.setColumnProperty("item", String.class.getName()).setTitle(
						"Item").setWidth(new Integer(85)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnCode = ColumnBuilder.getNew()
				.setColumnProperty("id", Long.class.getName()).setTitle("ID")
				.setWidth(new Integer(40)).setStyle(importeStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnaQuantity = ColumnBuilder.getNew()
				.setColumnProperty("quantity", Long.class.getName()).setTitle(
						"Quantity").setWidth(new Integer(80)).setStyle(
						importeStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnAmount = ColumnBuilder.getNew()
				.setColumnProperty("amount", Float.class.getName()).setTitle(
						"Amount").setWidth(new Integer(90))
				.setPattern("$ 0.00").setStyle(importeStyle).setHeaderStyle(
						headerStyle).build();

		GroupBuilder gb1 = new GroupBuilder();

//		 define the criteria column to group by (columnState)
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnState)
				.setGroupLayout(GroupLayout.DEFAULT_WITH_HEADER)
				.build();
		
		GroupBuilder gb2 = new GroupBuilder(); // Create another group (using another column as criteria)
		DJGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnBranch) // and we add the same operations for the columnAmount and
				.addFooterVariable(columnAmount,CalculationEnum.SUM) // columnaQuantity columns
				.addFooterVariable(columnaQuantity,	CalculationEnum.SUM)
				.setGroupLayout(GroupLayout.DEFAULT)
				.build();

		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);

		drb.addGroup(g1); // add group g1
		drb.addGroup(g2); // add group g2

		drb.setUseFullPageWidth(true);

		var cb = new DJBarChartBuilder();
		DJChart chart =  cb
						.setOperation(CalculationEnum.SUM)
                        .setColumnGroup((PropertyColumn) columnState)
                        .addSerie(columnAmount)
						.setPosition(DJChartOptions.POSITION_HEADER)
						.setShowLabels(true)
						.setHeight(200)
						.build();

		drb.addChart(chart); //add chart
		
		DJPieChartBuilder cb2 = new DJPieChartBuilder();
		DJChart chart2 =  cb2
						.setOperation(CalculationEnum.SUM)
                        .setColumnGroup((PropertyColumn) columnState)
						.addSerie(columnAmount)
						.setPosition(DJChartOptions.POSITION_HEADER)
						.setHeight(200)
						.build();

		drb.addChart(chart2); //add chart
		
		DJPieChartBuilder cb3 = new DJPieChartBuilder();
		DJChart chart3 =  cb3
		.setOperation(CalculationEnum.SUM)
        .setColumnGroup((PropertyColumn) columnState)
        .addSerie(columnAmount)
		.setPosition(DJChartOptions.POSITION_HEADER)
		.setHeight(200)
		.build();
		
		//drb.addChart(chart3); //add chart

		DynamicReport dr = drb.build();

		dr.getProperties().put("net.sf.jasperreports.chart.pie.ignore.duplicated.key","true"); //Needed in old API

		return dr;
	}

	public static void main(String[] args) throws Exception {
		ChartsMultipleReportTest test = new ChartsMultipleReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);

//		JasperDesignViewer.viewReportDesign(DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager()));
	}

}