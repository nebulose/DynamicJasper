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

package ar.com.fdvs.dj.domain;

import java.awt.Color;
import java.io.Serializable;

import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Stretching;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.LayoutUtils;
import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.design.JRDesignConditionalStyle;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.type.HorizontalImageAlignEnum;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.RotationEnum;
import net.sf.jasperreports.engine.type.VerticalImageAlignEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;

/**
 * Class that should be used to define the different styles in a friendly <br>
 * and strict way.<br>
 * <br>
 * Usage example:<br>
 * Style headerStyle = new Style();<br>
 * headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);<br>
 * headerStyle.setBorder(Border.PEN_2_POINT());<br>
 * headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);<br>
 * headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);<br>
 */
public class Style implements Serializable, Cloneable {

	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;

	private String name;
	private String parentStyleName;

	private Color backgroundColor = Color.WHITE;
	private Color textColor = Color.BLACK;

	private Font font = Font.ARIAL_MEDIUM;

	private Border border = Border.NO_BORDER();

	private Border borderTop = null;
	private Border borderBottom = null;
	private Border borderLeft = null;
	private Border borderRight = null;

	private Integer paddingBottom, paddingTop, paddingLeft, paddingRight;

	private Integer padding = 2;
	private Integer radius = 0;

	private ModeEnum transparency = ModeEnum.TRANSPARENT;

	private VerticalTextAlignEnum verticalTextAlign = VerticalTextAlignEnum.BOTTOM;
	private VerticalImageAlignEnum verticalImageAlign = VerticalImageAlignEnum.BOTTOM;

	@Deprecated
    private HorizontalTextAlignEnum horizontalTextAlign = HorizontalTextAlignEnum.LEFT;
    private HorizontalImageAlignEnum horizontalImageAlign = HorizontalImageAlignEnum.LEFT;

    private RotationEnum rotation = RotationEnum.NONE;

    private Stretching streching = Stretching.RELATIVE_TO_TALLEST_OBJECT;

    private boolean stretchWithOverflow = true;
    private boolean blankWhenNull = true;

    private String pattern;

    /**
     * If true and another style exists in the design with the same name, this style overrides the existing one.
     */
    private boolean overridesExistingStyle = false;

	public Style(){}

    public Style(String name){
    	this.name = name;
    }

    public Style(String name, String parentName){
    	this.name = name;
    	this.parentStyleName = parentName;
    }

	/**
	 * Creates a blank style (no default values).
	 * Useful when we need a style with a parent style, not defined properties (null ones) will be inherited
	 * from parent style
	 *
	 * @param name  style name
	 * @return  Style
	 */
	public static Style createBlankStyle(String name){
		Style style = new Style(name);

		style.setBackgroundColor(null);
		style.setBorderColor(null);
		style.setTransparency(null);
		style.setTextColor(null);
		style.setBorder(null);
		style.setFont(null);
		style.setPadding(null);
		style.setRadius(null);
		style.setVerticalTextAlignEnum(null);
		style.setHorizontalTextAlignEnum(null);
		style.setRotation(null);
		style.setStreching(null);

		return style;

	}

	public static Style createBlankStyle(String name, String parent){
		Style s = createBlankStyle(name);
		s.setParentStyleName(parent);
		return s;
	}

    public boolean isOverridesExistingStyle() {
		return overridesExistingStyle;
	}

	public void setOverridesExistingStyle(boolean overridesExistingStyle) {
		this.overridesExistingStyle = overridesExistingStyle;
	}

	public boolean isBlankWhenNull() {
		return blankWhenNull;
	}

	public void setBlankWhenNull(boolean blankWhenNull) {
		this.blankWhenNull = blankWhenNull;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		if (font != null)
			this.font = (Font) font.clone();
		else this.font = null;
	}

	public HorizontalTextAlignEnum getHorizontalAlign() {
		return horizontalTextAlign;
	}

	public void setHorizontalTextAlignEnum(HorizontalTextAlignEnum horizontalAlign) {
		this.horizontalTextAlign = horizontalAlign;
	}

	public Integer getPadding() {
		return padding;
	}

	public void setPadding(Integer padding) {
		this.padding = padding;
	}

	public Stretching getStreching() {
		return streching;
	}

	public void setStreching(Stretching streching) {
		this.streching = streching;
	}

	public boolean isStretchWithOverflow() {
		return stretchWithOverflow;
	}

	public void setStretchWithOverflow(boolean stretchWithOverflow) {
		this.stretchWithOverflow = stretchWithOverflow;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public ModeEnum getTransparency() {
		return transparency;
	}

	public void setTransparency(ModeEnum transparency) {
		this.transparency = transparency;
	}

	public boolean isTransparent(){
		return this.transparency.equals(ModeEnum.TRANSPARENT);
	}

	public void setTransparent(boolean transparent) {
		if (transparent)
			this.setTransparency(ModeEnum.TRANSPARENT);
		else
			this.setTransparency(ModeEnum.OPAQUE);
	}

	public VerticalTextAlignEnum getVerticalAlign() {
		return verticalTextAlign;
	}

	public void setVerticalTextAlignEnum(VerticalTextAlignEnum verticalAlign) {
		this.verticalTextAlign = verticalAlign;
	}

	public JRDesignConditionalStyle transformAsConditinalStyle() {
		JRDesignConditionalStyle ret = new JRDesignConditionalStyle();
		setJRBaseStyleProperties(ret);
		return ret;

	}

	public JRDesignStyle transform() {
		JRDesignStyle transformedStyle = new JRDesignStyle();
		transformedStyle.setName(this.name);
		transformedStyle.setParentStyleNameReference(this.parentStyleName);
		setJRBaseStyleProperties(transformedStyle);
		return transformedStyle;
	}

	protected void setJRBaseStyleProperties(JRBaseStyle transformedStyle) {
        if (getBorder()!=null){
            LayoutUtils.convertBorderToPen(getBorder(),transformedStyle.getLineBox().getPen());
        }

		if (getBorderBottom()!= null)
            LayoutUtils.convertBorderToPen(getBorderBottom(),transformedStyle.getLineBox().getBottomPen());
		if (getBorderTop()!= null)
            LayoutUtils.convertBorderToPen(getBorderTop(),transformedStyle.getLineBox().getTopPen());
		if (getBorderLeft()!= null)
            LayoutUtils.convertBorderToPen(getBorderLeft(),transformedStyle.getLineBox().getLeftPen());
		if (getBorderRight()!= null)
            LayoutUtils.convertBorderToPen(getBorderRight(),transformedStyle.getLineBox().getRightPen());

		//Padding
		transformedStyle.getLineBox().setPadding(getPadding());

		if (paddingBottom != null)
            transformedStyle.getLineBox().setBottomPadding(paddingBottom);
		if (paddingTop != null)
            transformedStyle.getLineBox().setTopPadding(paddingTop);
		if (paddingLeft != null)
            transformedStyle.getLineBox().setLeftPadding(paddingLeft);
		if (paddingRight != null)
            transformedStyle.getLineBox().setRightPadding(paddingRight);


		if (getHorizontalAlign() != null) {
			transformedStyle.setHorizontalTextAlign(getHorizontalAlign());
		}

		//Vertical TEXT aligns
		if (getVerticalAlign() != null) {
			transformedStyle.setVerticalTextAlign(getVerticalAlign());
		}

		//Horizontal Image align
		if (getHorizontalImageAlignEnum() != null) {
			transformedStyle.setHorizontalImageAlign(getHorizontalImageAlignEnum());
		}
		//Vertical Image align
		if (getVerticalImageAlignEnum() != null){
			transformedStyle.setVerticalImageAlign(getVerticalImageAlignEnum());
		}

		transformedStyle.setBlankWhenNull(Boolean.valueOf(blankWhenNull));

		//Font
		if (font != null) {
			transformedStyle.setFontName(font.getFontName());
			transformedStyle.setFontSize(font.getFontSize());
			transformedStyle.setBold(Boolean.valueOf(font.isBold()));
			transformedStyle.setItalic(Boolean.valueOf(font.isItalic()));
			transformedStyle.setUnderline(Boolean.valueOf(font.isUnderline()));
			transformedStyle.setPdfFontName(font.getPdfFontName());
			transformedStyle.setPdfEmbedded(Boolean.valueOf(font.isPdfFontEmbedded()));
			transformedStyle.setPdfEncoding(font.getPdfFontEncoding());
		}

		transformedStyle.setBackcolor(getBackgroundColor());
		transformedStyle.setForecolor(getTextColor());

		if (getTransparency() != null)
			transformedStyle.setMode(getTransparency());

		if (getRotation() != null)
			transformedStyle.setRotation(getRotation());

		if (getRadius() != null)
			transformedStyle.setRadius(Integer.valueOf(getRadius().intValue()));

		transformedStyle.setPattern(this.pattern);

		/*
		  This values are needed when exporting to JRXML
		 */
        //TODO Check if this is still necessary
        /*transformedStyle.setPen((byte)0);
		transformedStyle.setFill((byte)1);
		transformedStyle.setScaleImage(ScaleImageEnum.NO_RESIZE.getValue());*/

	}

	public Border getBorderBottom() {
		return borderBottom;
	}

	public void setBorderBottom(Border borderBottom) {
		this.borderBottom = borderBottom;
	}

	public Border getBorderLeft() {
		return borderLeft;
	}

	public void setBorderLeft(Border borderLeft) {
		this.borderLeft = borderLeft;
	}

	public Border getBorderRight() {
		return borderRight;
	}

	public void setBorderRight(Border borderRight) {
		this.borderRight = borderRight;
	}

	public Border getBorderTop() {
		return borderTop;
	}

	public void setBorderTop(Border borderTop) {
		this.borderTop = borderTop;
	}

    /**
     * use #Style.getBorder().getColor() instead
     * @return
     */
    @Deprecated
	public Color getBorderColor() {
        if (getBorder() == null)
            return null;
		return getBorder().getColor();
	}

    /**
     * Use #Style.setBorder(...) instead
     * @param borderColor
     */
    @Deprecated
	public void setBorderColor(Color borderColor) {
        if (getBorder() == null)
            return;

        this.getBorder().setColor(borderColor);
	}

	public RotationEnum getRotation() {
		return rotation;
	}

	public void setRotation(RotationEnum rotation) {
		this.rotation = rotation;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public Integer getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(Integer paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

    public Integer getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(Integer paddingTop) {
		this.paddingTop = paddingTop;
	}

	public Integer getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(Integer paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public Integer getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(Integer paddingRight) {
		this.paddingRight = paddingRight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentStyleName() {
		return parentStyleName;
	}

	public void setParentStyleName(String parentStyleName) {
		this.parentStyleName = parentStyleName;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Object clone() throws CloneNotSupportedException {
		Style style = (Style) super.clone();
		style.setFont(this.font);
		return style;
	}

	public HorizontalTextAlignEnum getHorizontalTextAlignEnum() {
		return horizontalTextAlign;
	}

	public VerticalTextAlignEnum getVerticalTextAlignEnum() {
		return verticalTextAlign;
	}

	public HorizontalImageAlignEnum getHorizontalImageAlignEnum() {
		return horizontalImageAlign;
	}

	public VerticalImageAlignEnum getVerticalImageAlignEnum() {
		return verticalImageAlign;
	}
}
