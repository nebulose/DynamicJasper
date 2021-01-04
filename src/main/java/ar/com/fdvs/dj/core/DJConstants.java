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

package ar.com.fdvs.dj.core;

/**
 * See value of constants here: 
 * http://jasperreports.sourceforge.net/api/constant-values.html
 * @author mamana
 *
 */
public interface DJConstants {

	String HEADER = "header";
	String FOOTER = "footer";

	int SUBREPORT_PARAMETER_MAP_ORIGIN_PARAMETER = 0;
	int SUBREPORT_PARAMETER_MAP_ORIGIN_FIELD = 1;

	int DATA_SOURCE_ORIGIN_PARAMETER = 0;
	int DATA_SOURCE_ORIGIN_FIELD = 1;
	int DATA_SOURCE_ORIGIN_REPORT_DATASOURCE = 2;
	int DATA_SOURCE_ORIGIN_USE_REPORT_CONNECTION = 10;

	int DATA_SOURCE_TYPE_COLLECTION 			= 0;
	int DATA_SOURCE_TYPE_ARRAY 					= 1;
	int DATA_SOURCE_TYPE_JPA 					= 2;
	int DATA_SOURCE_TYPE_RESULTSET				= 3;
	int DATA_SOURCE_TYPE_XML					= 4;
	int DATA_SOURCE_TYPE_HIBERNATE_ITERATE		= 5;
	int DATA_SOURCE_TYPE_HIBERNATE_LIST			= 5;
	int DATA_SOURCE_TYPE_HIBERNATE_SCROLL		= 6;
	int DATA_SOURCE_TYPE_CSV					= 7;
	int DATA_SOURCE_TYPE_MONDRIAN				= 8;
	int DATA_SOURCE_TYPE_OGNL_VALUE_STACK		= 9;
	int DATA_SOURCE_TYPE_JRDATASOURCE			= 10;

	int DATA_SOURCE_TYPE_SQL_CONNECTION			= 100; //This is in DJ only, not in JasperReports

	int COLOR_SCHEMA_WHITE = 0;
	int COLOR_SCHEMA_VIOLET = 1;
	int COLOR_SCHEMA_PINK = 2;
	int COLOR_SCHEMA_PINK_AND_BROWN = 3;
	int COLOR_SCHEMA_LIGHT_GREEN = 4;
	int COLOR_SCHEMA_BLUE = 5;
	int COLOR_SCHEMA_GRAY = 6;

	String QUERY_LANGUAGE_SQL = "sql";
	String QUERY_LANGUAGE_EJBQL = "ejbql";
	String QUERY_LANGUAGE_HQL = "hql";
	String QUERY_LANGUAGE_XPATH = "xPath";
	String QUERY_LANGUAGE_MONDRIAN = "mdx";

	int SUBREPORT_PARAM_ORIGIN_FIELD = 1;
	int SUBREPORT_PARAM_ORIGIN_PARAMETER = 2;
	int SUBREPORT_PARAM_ORIGIN_VARIABLE = 3;
	int SUBREPORT_PARAM_ORIGIN_CUSTOM = 4; //write your own expression

	String CUSTOM_EXPRESSION__PARAMETERS_MAP = "REPORT_PARAMETERS_MAP";
	
    String REPORT_LANGUAGE_JAVA = "java";
    String REPORT_LANGUAGE_GROOVY = "groovy";
    String REPORT_LANGUAGE_JAVASCRIPT = "javascript";

}
