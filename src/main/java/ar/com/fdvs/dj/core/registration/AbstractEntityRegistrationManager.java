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

package ar.com.fdvs.dj.core.registration;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.AbstractLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.Entity;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignParameter;

/**
 * Abstract Class used as base for the different Entities Registration Managers.<br>
 * <br>
 * Every implementation of this class should know how to register a given Entity<br>
 * and tranform it into any JasperReport object in order to add it to the <br>
 * JasperDesign.<br>
 * <br>
 * A Registration Manager is the first step required to create a report.<br>
 * A subclass should be created only when we want to add new features to DJ.<br>
 * Probably a new class from this hierarchy will imply a change to one or many <br>
 * Layout Managers.<br>
 * <br>
 * @see Entity
 * @see AbstractLayoutManager
 */
public abstract class AbstractEntityRegistrationManager implements DJConstants {

	private static final Logger log = LoggerFactory.getLogger(AbstractEntityRegistrationManager.class);

	private DynamicJasperDesign djd;

//	private Collection columns;

	private DynamicReport dynamicReport;
	
	private LayoutManager layoutManager;

	public AbstractEntityRegistrationManager(DynamicJasperDesign djd, DynamicReport dr, LayoutManager layoutManager) {
		this.djd = djd;
		this.dynamicReport = dr;
		this.layoutManager = layoutManager;
	}

	public final void registerEntities(Collection entities) throws EntitiesRegistrationException {
//		log.debug("Registering entities: " + this.getClass().getName());
		try {
			if (entities!=null) {
				for (Object entity1 : entities) {
					Entity entity = (Entity) entity1;
					registerEntity(entity);
				}
			}
		} catch (RuntimeException e) {
			throw new EntitiesRegistrationException(e.getMessage(),e);
		}
	}

	/**
	 * Registers in the report's JasperDesign instance whatever is needed to
	 * show a given entity.
	 * @throws EntitiesRegistrationException
	 */
	protected abstract void registerEntity(Entity entity);

	/**
	 * Transforms a DynamicJasper entity into a JasperReport one
	 * (JRDesignField, JRDesignParameter, JRDesignVariable)
	 * @throws EntitiesRegistrationException
	 */
	protected abstract Object transformEntity(Entity entity) throws JRException;

	protected void registerCustomExpressionParameter(String name, CustomExpression customExpression) {
		if (customExpression == null){
			log.debug("No customExpression for calculation for property " + name );
			return;
		}
		JRDesignParameter dparam = new JRDesignParameter();
		dparam.setName(name);
		dparam.setValueClassName(CustomExpression.class.getName());
		log.debug("Registering customExpression parameter for property " + name );
		try {
			getDjd().addParameter(dparam);
		} catch (JRException e) {
			throw new EntitiesRegistrationException(e.getMessage(),e);
		}
		getDjd().getParametersWithValues().put(name, customExpression);
	}

//	public Collection getColumns() {
//		return columns;
//	}

	public DynamicReport getDynamicReport() {
		return dynamicReport;
	}

	public LayoutManager getLayoutManager() {
		return layoutManager;
	}

	public DynamicJasperDesign getDjd() {
		return djd;
	}

}
