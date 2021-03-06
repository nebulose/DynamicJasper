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
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.chart.DJChart;
import ar.com.fdvs.dj.domain.chart.builder.DJBarChartBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import net.sf.jasperreports.engine.type.CalculationEnum;
import net.sf.jasperreports.engine.type.HorizontalImageAlignEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import net.sf.jasperreports.view.JasperViewer;

public class FullFeatureReportTest extends BaseDjReportTest {

	public DynamicReport buildReport() throws Exception {

		Style detailStyle = new Style();
		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD); headerStyle.setBorder(Border.PEN_2_POINT());
		headerStyle.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER); headerStyle.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);

		Style titleStyle = new Style();
		titleStyle.setFont(new Font(18,Font._FONT_VERDANA,true));
		Style importeStyle = new Style();
		importeStyle.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.PEN_1_POINT()); oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);oddRowStyle.setTransparency(ModeEnum.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		Integer margin = 20;
			drb.setTitleStyle(titleStyle)
			.setTitle("November " + getYear() +" sales report")					//defines the title of the report
			.setSubtitle("The items in this report correspond "
					+"to the main products: DVDs, Books, Foods and Magazines")
			.setDetailHeight(15)
			.setLeftMargin(margin)
			.setRightMargin(margin)
			.setTopMargin(margin)
			.setBottomMargin(margin)
			.setPrintBackgroundOnOddRows(true)
			.setPrintColumnNames(false)
			.setOddRowBackgroundStyle(oddRowStyle)
			.addFirstPageImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/logo_fdv_solutions_60.png", 197, 60, HorizontalImageAlignEnum.LEFT)
			.addFirstPageImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/dynamicJasper_60.jpg", 300, 60, HorizontalImageAlignEnum.RIGHT)
			.addImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/logo_fdv_solutions_60.png", 100, 30, HorizontalImageAlignEnum.LEFT)
			.addImageBanner(System.getProperty("user.dir") +"/target/test-classes/images/dynamicJasper_60.jpg", 150, 30, HorizontalImageAlignEnum.RIGHT);

		AbstractColumn columnState = ColumnBuilder.getNew().setColumnProperty("state", String.class.getName())
			.setTitle("State").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnBranch = ColumnBuilder.getNew().setColumnProperty("branch", String.class.getName())
			.setTitle("Branch").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaProductLine = ColumnBuilder.getNew().setColumnProperty("productLine", String.class.getName())
			.setTitle("Product Line").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaItem = ColumnBuilder.getNew().setColumnProperty("item", String.class.getName())
			.setTitle("Item").setWidth(new Integer(85))
			.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnCode = ColumnBuilder.getNew().setColumnProperty("id", Long.class.getName())
			.setTitle("ID").setWidth(new Integer(40))
			.setStyle(importeStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaQuantity = ColumnBuilder.getNew().setColumnProperty("quantity", Long.class.getName())
			.setTitle("Quantity").setWidth(new Integer(80))
			.setStyle(importeStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnAmount = ColumnBuilder.getNew().setColumnProperty("amount", Float.class.getName())
			.setTitle("Amount").setWidth(new Integer(90)).setTextFormatter(NumberFormat.getCurrencyInstance())
			.setStyle(importeStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaCode = ColumnBuilder.getNew().setColumnProperty("code.code", String.class.getName())
		.setTitle("Code").setWidth(new Integer(85))
		.setStyle(detailStyle).setHeaderStyle(headerStyle).build();		
		
		Format textFormatter = new Format(){

			public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
				if (obj == null || Boolean.FALSE.equals(obj))
					toAppendTo.append("No");
				else
					toAppendTo.append("Yes");
				
				return toAppendTo;
			}

			public Object parseObject(String source, ParsePosition pos) {
				return null;
			}};
			
		AbstractColumn columnavailable = ColumnBuilder.getNew().setColumnProperty("isAvailable", Boolean.class.getName())
		.setTitle("In stock").setWidth(new Integer(40)).setTextFormatter(textFormatter)
		.setStyle(importeStyle).setHeaderStyle(headerStyle).build();


		GroupBuilder gb1 = new GroupBuilder();
		DJGroup g1 = gb1.setCriteriaColumn((PropertyColumn) columnState)		//define the criteria column to group by (columnState)
			.addFooterVariable(columnAmount,CalculationEnum.SUM)		//tell the group place a variable in the footer
																					//of the column "columnAmount" with the SUM of all
																					//values of the columnAmount in this group.

			.addFooterVariable(columnaQuantity,CalculationEnum.SUM)	//idem for the columnaQuantity column
			.setGroupLayout(GroupLayout.DEFAULT_WITH_HEADER)				//tells the group how to be shown, there are many
																					//posibilities, see the GroupLayout for more.
			.build();

		GroupBuilder gb2 = new GroupBuilder();										//Create another group (using another column as criteria)
		DJGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnBranch)		//and we add the same operations for the columnAmount and
			.addFooterVariable(columnAmount,CalculationEnum.SUM)		//columnaQuantity columns
			.addFooterVariable(columnaQuantity,CalculationEnum.SUM)
			.build();

		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaQuantity);
		drb.addColumn(columnAmount);
		drb.addColumn(columnavailable);
		drb.addColumn(columnaCode);

		drb.addGroup(g1);	//add group g1
		drb.addGroup(g2);	//add group g2

		drb.setUseFullPageWidth(true);

		//Autotext
		drb.addAutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_HEADER, AutoText.ALIGNMENT_LEFT,AutoText.PATTERN_DATE_DATE_TIME);
		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_LEFT);

		//i18N
		drb.setReportLocale(Locale.ENGLISH);

		//Charts
		DJBarChartBuilder cb = new DJBarChartBuilder();
		DJChart chart =  cb
						.setOperation(CalculationEnum.SUM)
						.setColumnGroup((PropertyColumn) columnBranch).setHeight(150)
						.addSerie(columnAmount)
						.build();

		drb.addChart(chart); //add chart

		DynamicReport dr = drb.build();
		return dr;
	}

	public static void main(String[] args) throws Exception {
		FullFeatureReportTest test = new FullFeatureReportTest();
		test.testReport();
		JasperViewer.viewReport(test.jp);
//		JasperDesignViewer.viewReportDesign(test.jr);
//		JRXmlWriter.writeReport(test.jr, "e:\\temp\\reporte.jrxml", "UTF-8");
	}

}
