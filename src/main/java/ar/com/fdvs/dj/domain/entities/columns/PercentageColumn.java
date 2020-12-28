package ar.com.fdvs.dj.domain.entities.columns;

import ar.com.fdvs.dj.core.CoreException;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.Entity;
import net.sf.jasperreports.engine.type.CalculationEnum;

/**
 * This column shows a percentage relative to another column.
 * 
 * @author mamana and Ricardo Mariaca
 *
 */
public class PercentageColumn extends AbstractColumn {
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	private PropertyColumn percentageColumn;

	public String getTextForExpression() {
		throw new CoreException("invalid operation on PercentageColumn");
	}
	
	public String getTextForExpression(DJGroup group) {
		return "new Double((" + getPercentageColumn().getTextForExpression() + ").doubleValue() / $V{" + getReportName() + "_" + getGroupVariableName(group) + "}.doubleValue())";
	}	

	/**
	 * Returns the formula for the percentage
	 * @param group
	 * @param type
	 * @return
	 */
	public String getTextForExpression(DJGroup group, DJGroup childGroup, String type) {
		return "new Double( $V{" + getReportName() + "_" +  getGroupVariableName(childGroup) + "}.doubleValue() / $V{" + getReportName() + "_" + getGroupVariableName(type,group.getColumnToGroupBy().getColumnProperty().getProperty()) + "}.doubleValue())";
	}

	public String getValueClassNameForExpression() {
		return Number.class.getName();
	}

	public String getGroupVariableName(String type, String columnToGroupByProperty) {
		return "variable-" + type + "_" + columnToGroupByProperty + "_" + getPercentageColumn().getColumnProperty().getProperty() + "_percentage";
	}

	public String getVariableClassName(CalculationEnum op) {
		if (op == CalculationEnum.COUNT || op == CalculationEnum.DISTINCT_COUNT)
			return Long.class.getName();
		else
			return Number.class.getName();
	}

	public String getInitialExpression(CalculationEnum op) {
		return "new java.lang.Long(\"0\")";
	}

	/**
	 * The group which the variable will be inside (mostly for reset)
	 * @param group (may be null)
	 * @return
	 */
	public String getGroupVariableName(DJGroup group) {
		String columnToGroupByProperty = group.getColumnToGroupBy().getColumnProperty().getProperty();
		return "variable-" + columnToGroupByProperty + "_" + getPercentageColumn().getColumnProperty().getProperty() + "_percentage";
	}
	
	public void setPercentageColumn(PropertyColumn percentageColumn) {
		this.percentageColumn = percentageColumn;
	}

	public PropertyColumn getPercentageColumn() {
		return percentageColumn;
	}

}
