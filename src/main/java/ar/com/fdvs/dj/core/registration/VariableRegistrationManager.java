package ar.com.fdvs.dj.core.registration;

import ar.com.fdvs.dj.core.DJException;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicJasperDesign;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.entities.DJVariable;
import ar.com.fdvs.dj.domain.entities.Entity;
import ar.com.fdvs.dj.util.ExpressionUtils;
import ar.com.fdvs.dj.util.LayoutUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.type.IncrementTypeEnum;
import net.sf.jasperreports.engine.type.ResetTypeEnum;

public class VariableRegistrationManager extends
		AbstractEntityRegistrationManager {

	public VariableRegistrationManager(DynamicJasperDesign jd, DynamicReport dr, LayoutManager layoutManager) {
		super(jd,dr,layoutManager);
	}	
	
	@Override
	protected void registerEntity(Entity entity) {
		try {
			JRDesignVariable jrvar = (JRDesignVariable) transformEntity(entity);
			getDjd().addVariable(jrvar);
		} catch (JRException e) {
			throw new DJException("Problem registering a DJVariable: " + e.getMessage(), e);
		}

	}

	@Override
	protected Object transformEntity(Entity entity) throws JRException {
		DJVariable var = (DJVariable)entity;
		JRDesignVariable jrvar = new JRDesignVariable();
		jrvar.setName(var.getName());
		jrvar.setValueClassName(var.getClassName());
		
		if (var.getCalculation() != null){
			jrvar.setCalculation(var.getCalculation());
		}
		
		String expressionParamName = var.getName() + "_expression";
		JRDesignExpression expression = ExpressionUtils.createAndRegisterExpression(getDjd(), expressionParamName, var.getExpression());		
		jrvar.setExpression(expression);

		if (var.getInitialValueExpression() != null){
			String initialValueExpressionParamName = var.getName() + "_initalValueExpression";
			JRDesignExpression initialValueExpression = ExpressionUtils.createAndRegisterExpression(getDjd(), initialValueExpressionParamName, var.getInitialValueExpression());
			jrvar.setExpression(initialValueExpression);
		}
		
		if (var.getResetType() != null){
			jrvar.setResetType(var.getResetType());
		}		
		
		if (ResetTypeEnum.GROUP == var.getResetType()){
			JRDesignGroup jrgroup = LayoutUtils.getJRDesignGroup(getDjd(),getLayoutManager(), var.getResetGroup());
			jrvar.setResetGroup(jrgroup);
		}
		
		if (var.getIncrementType() != null){
			jrvar.setIncrementType(var.getIncrementType() );
		}
		
		if (IncrementTypeEnum.GROUP == var.getIncrementType()){
			JRDesignGroup jrgroup = LayoutUtils.getJRDesignGroup(getDjd(),getLayoutManager(), var.getResetGroup());
			jrvar.setIncrementGroup(jrgroup);
		}		
		
		return jrvar;
	}

}
