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

package ar.com.fdvs.dj.domain.builders;

import java.awt.Color;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.RotationEnum;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;

public class StyleBuilder {

	Style style = null;

	public StyleBuilder(boolean blank) {
		super();

		if (blank)
			style = Style.createBlankStyle(null);
		else
			style = new Style();

	}

	public StyleBuilder(boolean blank, String name) {
		super();

		if (blank)
			style = Style.createBlankStyle(null);
		else
			style = new Style();

		style.setName(name);
	}

	public StyleBuilder(boolean blank, String name, String parentName) {
		super();

		if (blank)
			style = Style.createBlankStyle(null);
		else
			style = new Style();

		style.setName(name);
		style.setParentStyleName(parentName);
	}

	public Style build(){
		return style;
	}


	public StyleBuilder setName(String name){
		style.setName(name);
		return this;
	}

	public StyleBuilder setPattern(String pattern){
		style.setPattern(pattern);
		return this;
	}

	public StyleBuilder setFont(Font font){
		style.setFont(font);
		return this;
	}

	public StyleBuilder setHorizontalTextAlignEnum(HorizontalTextAlignEnum horizontalAlign){
		style.setHorizontalTextAlign(horizontalAlign);
		return this;
	}

	public StyleBuilder setVerticalTextAlignEnum(VerticalTextAlignEnum verticalAlign){
		style.setVerticalTextAlign(verticalAlign);
		return this;
	}

	public StyleBuilder setStretching(StretchTypeEnum streching){
		style.setStreching(streching);
		return this;
	}

	public StyleBuilder setTextColor(Color textColor){
		style.setTextColor(textColor);
		return this;
	}

	public StyleBuilder setBackgroundColor(Color backgroundColor){
		style.setBackgroundColor(backgroundColor);
		return this;
	}

	public StyleBuilder setTransparency(ModeEnum transparency){
		style.setTransparency(transparency);
		return this;
	}

	public StyleBuilder setTransparent(boolean isTransparent){
		style.setTransparent(isTransparent);
		return this;
	}

	public StyleBuilder setBorderBottom(Border borderBottom) {
		style.setBorderBottom(borderBottom);
		return this;
	}

	public StyleBuilder setBorderColor(Color borderColor) {
		if (style.getBorder() == null)
			return this;

		style.getBorder().setColor(borderColor);
		return this;
	}

	public StyleBuilder setBorderLeft(Border borderLeft) {
		style.setBorderLeft(borderLeft);
		return this;
	}

	public StyleBuilder setBorderRight(Border borderRight) {
		style.setBorderRight(borderRight);
		return this;
	}

	public StyleBuilder setBorderTop(Border borderTop) {
		style.setBorderTop(borderTop);
		return this;
	}

	public StyleBuilder setPadding(Integer padding) {
		style.setPadding(padding);
		return this;
	}

	public StyleBuilder setPaddingBottom(Integer paddingBottom) {
		style.setPaddingBottom(paddingBottom);
		return this;
	}

	/**
	 * @deprecated due to miss spelling
	 * @param paddingBotton
	 * @return
	 */
	public StyleBuilder setPaddingBotton(Integer paddingBotton) {
		return setPaddingBottom(paddingBotton);
	}

	public StyleBuilder setPaddingLeft(Integer paddingLeft) {
		style.setPaddingLeft(paddingLeft);
		return this;
	}

	public StyleBuilder setPaddingRight(Integer paddingRight) {
		style.setPaddingRight(paddingRight);
		return this;
	}

	public StyleBuilder setPaddingTop(Integer paddingTop) {
		style.setPaddingTop(paddingTop);
		return this;
	}

	public StyleBuilder setParentStyleName(String parentStyleName) {
		style.setParentStyleName(parentStyleName);
		return this;
	}


	public StyleBuilder setRotation(RotationEnum rotation) {
		style.setRotation(rotation);
		return this;
	}

	public StyleBuilder setBorder(Border border) {
		style.setBorder(border);
		return this;
	}
	
	public StyleBuilder setStretchWithOverflow(boolean stretchWithOverflow) {
		style.setStretchWithOverflow(stretchWithOverflow);
		return this;
	}
	


}
