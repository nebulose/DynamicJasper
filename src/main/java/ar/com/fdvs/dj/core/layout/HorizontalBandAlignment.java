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

import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;

/**
 * @author msimone
 *
 */
public abstract class HorizontalBandAlignment {

	/**
	 * To be used with AutoText class constants ALIGMENT_LEFT, ALIGMENT_CENTER and ALIGMENT_RIGHT
	 * @param aligment
	 * @return
	 */
	public static HorizontalBandAlignment buildAligment(HorizontalTextAlignEnum aligment){
		if (aligment == HorizontalTextAlignEnum.RIGHT)
			return RIGHT;
		else if (aligment == HorizontalTextAlignEnum.LEFT)
			return LEFT;

		return CENTER;
	}

	public static final HorizontalBandAlignment RIGHT = new HorizontalBandAlignment() {
		public void align(int totalWidth, int offset, JRDesignBand band, JRDesignElement element) {
			int width = totalWidth - element.getWidth() - offset;
			element.setX(width);
			band.addElement(element);
		}

		public HorizontalTextAlignEnum getAlignment() {
			return HorizontalTextAlignEnum.RIGHT;
		}
	};

	public static final HorizontalBandAlignment LEFT = new HorizontalBandAlignment() {
		public void align(int totalWidth, int offset, JRDesignBand band, JRDesignElement element) {
			element.setX(element.getX() + offset);
			band.addElement(element);
		}

		public HorizontalTextAlignEnum getAlignment() {
			return HorizontalTextAlignEnum.LEFT;
		}
	};

	public static final HorizontalBandAlignment CENTER = new HorizontalBandAlignment() {
		public void align(int totalWidth, int offset, JRDesignBand band, JRDesignElement element) {
			element.setX(totalWidth/2 - element.getWidth()/2 + offset);
			band.addElement(element);
		}

		public HorizontalTextAlignEnum getAlignment() {
			return HorizontalTextAlignEnum.CENTER;
		}
	};

	public abstract HorizontalTextAlignEnum getAlignment();
	public abstract void align(int totalWidth, int offset, JRDesignBand band, JRDesignElement element);

}
