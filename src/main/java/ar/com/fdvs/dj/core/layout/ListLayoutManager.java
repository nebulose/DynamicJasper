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

package ar.com.fdvs.dj.core.layout;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignTextField;

/**
 * Simple Layout Manager recommended when we want to get a ready to operate <br>
 * Excel plain list from the report.<br>
 * <br>
 * Groups and many style options will be ignored.
 */
public class ListLayoutManager extends AbstractLayoutManager {

    private static final Logger log = LoggerFactory.getLogger(ListLayoutManager.class);

    protected Map<String, Object> referencesMap = new HashMap<String, Object>();

    public Map<String, Object> getReferencesMap() {
        return referencesMap;
    }

    protected void startLayout() {
        getReport().getOptions().setColumnsPerPage(1);
        getReport().getOptions().setColumnSpace(0);
        getDesign().setColumnCount(1);
        getDesign().setColumnWidth(getReport().getOptions().getColumnWidth());
        super.startLayout();
        if (getReport().getOptions().isPrintColumnNames()) {
            generateHeaderBand();
        }
        getDesign().setIgnorePagination(getReport().getOptions().isIgnorePagination());
    }

    protected void transformDetailBandTextField(AbstractColumn column, JRDesignTextField textField) {
        log.debug("transforming detail band text field...");
        textField.setPrintRepeatedValues(true);
    }

    protected void generateHeaderBand() {
        log.debug("generating header band...");
        JRDesignBand header = (JRDesignBand) getDesign().getColumnHeader();
        if (header == null) {
            header = new JRDesignBand();
            getDesign().setColumnHeader(header);
        }
        generateHeaderBand(header);
    }
}
