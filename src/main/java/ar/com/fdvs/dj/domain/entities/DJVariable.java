package ar.com.fdvs.dj.domain.entities;

import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJBaseElement;
import ar.com.fdvs.dj.domain.constants.DJVariableIncrementType;
import ar.com.fdvs.dj.domain.constants.DJVariableResetType;
import net.sf.jasperreports.engine.type.CalculationEnum;

public class DJVariable extends DJBaseElement {
	
	public DJVariable(){}

	public DJVariable(String name, String className, CalculationEnum calculation, CustomExpression expression) {
		super();
		this.name = name;
		this.className = className;
		this.calculation = calculation;
		this.expression = expression;
	}
	private static final long serialVersionUID = Entity.SERIAL_VERSION_UID;
	
	private String name;
	private String className;
	private CalculationEnum calculation = CalculationEnum.NOTHING;
	
	private DJGroup resetGroup;
	private DJVariableResetType resetType = DJVariableResetType.REPORT;
	
	private DJGroup incrementGroup;
	private DJVariableIncrementType incrementType = DJVariableIncrementType.NONE;
	
	private CustomExpression expression;
	private CustomExpression initialValueExpression;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public CalculationEnum getCalculation() {
		return calculation;
	}
	public void setCalculation(CalculationEnum calculation) {
		this.calculation = calculation;
	}
	public CustomExpression getExpression() {
		return expression;
	}
	public void setExpression(CustomExpression expression) {
		this.expression = expression;
	}
	public CustomExpression getInitialValueExpression() {
		return initialValueExpression;
	}
	public void setInitialValueExpression(CustomExpression initialValueExpression) {
		this.initialValueExpression = initialValueExpression;
	}
	public DJGroup getResetGroup() {
		return resetGroup;
	}
	public void setResetGroup(DJGroup resetGroup) {
		this.resetGroup = resetGroup;
	}
	public void setResetType(DJVariableResetType resetType) {
		this.resetType = resetType;
	}
	public DJVariableResetType getResetType() {
		return resetType;
	}
	public DJGroup getIncrementGroup() {
		return incrementGroup;
	}
	public void setIncrementGroup(DJGroup incrementGroup) {
		this.incrementGroup = incrementGroup;
	}
	public DJVariableIncrementType getIncrementType() {
		return incrementType;
	}
	public void setIncrementType(DJVariableIncrementType incrementType) {
		this.incrementType = incrementType;
	}
	
}
