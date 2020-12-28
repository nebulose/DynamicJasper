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


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import ar.com.fdvs.dj.core.DJServletHelper;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.output.ReportWriter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;

public class HtmlExportReportTest extends FastReportTest {

    @Override
    public void testReport() throws Exception {
        dr = buildReport();

        /*
          Get a JRDataSource implementation
         */
        JRDataSource ds = getDataSource();


        /*
          Creates the JasperReport object, we pass as a Parameter
          the DynamicReport, a new ClassicLayoutManager instance (this
          one does the magic) and the JRDataSource
         */
        jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);

        /*
          Creates the JasperPrint object, we pass as a Parameter
          the JasperReport object, and the JRDataSource
         */
        log.debug("Filling the report");
        if (ds != null)
            jp = JasperFillManager.fillReport(jr, params, ds);
        else
            jp = JasperFillManager.fillReport(jr, params);

        log.debug("Filling done!");
        log.debug("Exporting the report (pdf, xls, etc)");

        //Exporting directly to a Response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        DJServletHelper.exportToHtml(request, response,"/images", jp, null);

        //Exporting and returning an InputStream
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        DJServletHelper.setPageTreshold(0);
        InputStream is = DJServletHelper.exportToHtml(request2, "/images", jp, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ReportWriter.copyStreams(is,out);

        //Comparing both generated html. They should be the same
        assertNotNull(response.getContentAsString());
        assertTrue("not empty", !response.getContentAsString().equals(""));
        assertTrue("not empty", !new String(out.toByteArray()).equals(""));

        log.debug("test finished");

    }

    public void testReport2() throws Exception {
        dr = buildReport();

        /*
          Creates the JasperReport object, we pass as a Parameter
          the DynamicReport, a new ClassicLayoutManager instance (this
          one does the magic) and the JRDataSource
         */
        jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);

        log.debug("Filling done!");
        log.debug("Exporting the report (pdf, xls, etc)");

        //Exporting directly to a Response
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        DJServletHelper.exportToHtml(request, response,"/images", dr, getLayoutManager(),getDataSource(),new HashMap(),null);

        //Exporting and returning an InputStream
        MockHttpServletRequest request2 = new MockHttpServletRequest();
        InputStream is = DJServletHelper.exportToHtml(request2,"/images", dr, getLayoutManager(),getDataSource(),new HashMap(),null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ReportWriter.copyStreams(is,out);

        //Comparing both generated html. They should be the same
        //assertEquals(response.getContentAsString(),new String(out.toByteArray()));

        assertTrue("not empty", !response.getContentAsString().equals(""));
        assertTrue("not empty", !new String(out.toByteArray()).equals(""));

        log.debug("test finished");

    }


	public static void main(String[] args) throws Exception {
		HtmlExportReportTest test = new HtmlExportReportTest();
		test.testReport();
		test.testReport2();
	}

}
