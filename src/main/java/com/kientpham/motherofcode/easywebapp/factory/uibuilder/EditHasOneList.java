package com.kientpham.motherofcode.easywebapp.factory.uibuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.HtmlEditPageBase;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public class EditHasOneList extends HtmlEditPageBase {

	@Override
	public String buildHeaderForEdit(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}

	@Override
	protected String generateFieldForm(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {
			if (field.isShowInEdit()) {				
				if (field.getLookupType().isEmpty()) {
					String style=(!field.getUiType().equals("checkbox"))? "class=\"input-sm form-control-sm form-control mw160\"":"";
					code += String.format(
							"\r\n							<div class=\"row form-group\">\r\n"
							+ "								<div class=\"col col-sm-3\">\r\n"
							+ "									<label class=\"form-control-label mw100\">%2$s</label>	\r\n"
							+ "								</div>\r\n"
							+ "								<div class=\"col col-md-8\">\r\n"
							+ "									<input id=\"%1$s\" type=\"%4$s\" %3$s %5$s/>									\r\n"
							+ "								</div>\r\n" 
							+ "							</div>\r\n",
							field.getName(), CommonUtils.getColumnName(field.getName()), field.getRequired(), field.getUiType() ,style);
				}else{
					code+=String.format(
							"\r\n							<div class=\"row form-group\">\r\n" + 
							"								<div class=\"col col-sm-3\">\r\n" + 
							"									<label class=\"control-label mb-1 mw100\">%2$s</label>	\r\n" + 
							"								</div>\r\n" + 
							"									<div class=\"col col-md-4\">\r\n" + 
							"									<select id=\"%1$s\" name=\"cmbType\" %3$s class=\"mw160 input-sm form-control-sm form-control\">\r\n" + 
							"									</select>\r\n" + 
							"								</div>\r\n" + 
							"							</div>\r\n",field.getName(), CommonUtils.getColumnName(field.getName()),field.getRequired());
				}
			}
		}
		return String.format(
				"					<div class=\"row\">\r\n" + 
				"						<div class=\"col col-md-8\">\r\n"+
				"							%1$s"+
				"						</div>\r\n", code) ;
	}
	
	@Override
	protected String generateTable(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		if (omnibusDTO.getTransaction().getEntity().getListMulitpleJoinTable().size() == 0)
			return "";
		String joinEntityName = omnibusDTO.getTransaction().getEntity().getListMulitpleJoinTable().get(0).getType();
		return String.format(
				"						<div class=\"col col-sm-4\">\r\n"+
				"							%1$s\r\n"+
				"						</div>\r\n"+
				"					</div>\r\n", super.generateSingleTable(omnibusDTO,joinEntityName)); 

	}

}
