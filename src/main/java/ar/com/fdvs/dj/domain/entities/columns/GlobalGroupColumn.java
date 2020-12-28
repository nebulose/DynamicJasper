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

package ar.com.fdvs.dj.domain.entities.columns;

import ar.com.fdvs.dj.domain.ColumnProperty;
import ar.com.fdvs.dj.domain.entities.Entity;
import net.sf.jasperreports.engine.type.CalculationEnum;

/**
 * Column created to handle global variables.
 */
public class GlobalGroupColumn extends PropertyColumn {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	public GlobalGroupColumn() {
		ColumnProperty columnProperty = new ColumnProperty("global", String.class.getName());
		setTitle("global");
		setColumnProperty(columnProperty);
	}

	public GlobalGroupColumn(String name) {
		ColumnProperty columnProperty = new ColumnProperty(name, String.class.getName());
		setTitle("global");
		setColumnProperty(columnProperty);
	}

	public String getGroupVariableName(String type, String columnToGroupByProperty) {
		return null;
	}

	public String getInitialExpression(CalculationEnum op) {
		return null;
	}

	public String getTextForExpression() {
		return "\"" + getTitle() + "\"";
	}

	public String getValueClassNameForExpression() {
		return String.class.getName();
	}

	public String getVariableClassName(CalculationEnum op) {
		return null;
	}

}
