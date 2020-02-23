package com.kientpham.motherofcode.easywebapp.factory.interfaces;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public class HtmlEditPageBase {
	
	public String buildHeaderForEdit(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}
	
	public String buildBodyForEditPage(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String idFieldName = omnibusDTO.getTransaction().getEntity().getIDField().getName();
		return String.format(
				"	<form class=\"modal fade\" name=\"%2$s_edit\" id=\"edit%1$s\" method=\"POST\" role=\"dialog\" data-backdrop=\"static\">			\r\n"
						+ "		<div class=\"modal-dialog modal-lg\">\r\n" + "			<!-- Modal content-->\r\n"
						+ "			<div class=\"modal-content\">\r\n"
						+ "				<div class=\"modal-header\">\r\n"
						+ "					<h4 class=\"modal-title\" id=\"dialogTitle\">%1$s Detail</h4>\r\n"
						+ "					<button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\r\n"
						+ "				</div>\r\n"
						+ "				<div class=\"au-card card-body card-block container\" >\r\n"
						+ "					<input type=\"hidden\" id=\"%3$s\" />\r\n"
						+ "						%4$s\r\n"
						+ "						%5$s\r\n"						
						+ "					<div class=\"row d-flex justify-content-center\">\r\n"
						+ "						<button class=\"btn btn-primary m-2\" id=\"btnSave\">Save</button>\r\n"
						+ "						<button type=\"button\" class=\"btn btn-secondary m-2\" data-dismiss=\"modal\">Cancel</button>\r\n"
						+ "					</div>\r\n" 
						+ "					<div class=\"row\">\r\n"
						+ "						<div class=\"row-left\">&nbsp;</div>\r\n"
						+ "						<div class=\"row-right\">\r\n"
						+ "							<span id=\"message\" style=\"color: red;\"></span>\r\n"
						+ "						</div>\r\n" 
						+ "					</div>\r\n" 
						+ "				</div>\r\n"
						+ "				<input type=\"submit\" value=\"&nbsp;\" class=\"hide\">\r\n"
						+ "			</div>\r\n"
						+ "		</div>\r\n"
						+ "	</form>\r\n",
				entityName, entityName.toLowerCase(), idFieldName, this.generateFieldForm(omnibusDTO),
				this.generateTable(omnibusDTO)); 
	}

	protected String generateFieldForm(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = "";		
		boolean isNewRow=true;
		for (Field field: omnibusDTO.getTransaction().getEntity().getFields()) {
			if (field.isShowInEdit()) {
				if (!field.getUIText().isEmpty()) {
					if (!isNewRow) code+="					</div>\r\n";
					code += String.format(
							"					<div class=\"row\">\r\n" + 
							"						<div class=\"col col-md-12\">\r\n" + 
							"							<div class=\"row form-group\">\r\n" + 
							"								<div class=\"col col-sm-2\">\r\n" + 
							"									<label class=\"control-label mb-1 mw160\">%2$s</label>	\r\n" + 
							"								</div>\r\n" + 
							"								<div class=\"col col-sm-9 mw350\">\r\n" + 
							"									<input id=\"%1$s\" %3$s type=\"%4$s\" class=\"mw350 input-sm form-control-sm form-control\" />\r\n" + 
							"								</div>\r\n" + 
							"							</div>\r\n" + 
							"						</div>\r\n" + 
							"					</div>\r\n",
							field.getName(), CommonUtils.getColumnName(field.getName()), field.getRequired(), field.getUiType());
					isNewRow=true;
				}else{							
					if (isNewRow) {
						code+=String.format(
								"					<div class=\"row\">\r\n"+
								"						<div class=\"col col-md-6\">\r\n" +
								"							%1$s\r\n"+
								"						</div>\r\n", this.getInputControl(field));
						isNewRow=false;
					}else {
						code+=String.format(
								"					<div class=\"col col-md-6\">\r\n"+
								"						%1$s\r\n"+
								"					</div>\r\n"+
								"				</div>\r\n", this.getInputControl(field)) ;						
						isNewRow=true;
					}					
				}				
			}			
		}
		if (!isNewRow) code+="				</div>\r\n";
		return code;
	}
	
	private String getInputControl(Field field) {
		String code="";		
		if (field.getLookupType().isEmpty()) {
			String with="6";
			if (field.getUiType().equals("Date")) 
				with="4";
			code=String.format(
				 
					"							<div class=\"row form-group\">\r\n" + 
					"								<div class=\"col col-sm-4 \">\r\n" + 
					"									<label class=\"form-control-label mw160\">%2$s</label>	\r\n" + 
					"								</div>\r\n" + 
					"								<div class=\"col col-md-%5$s\">\r\n" + 
					"									<input id=\"%1$s\" autofocus type=\"%4$s\" %3$s  class=\"input-sm form-control-sm form-control mw200\"/>\r\n" + 
					"								</div>\r\n" + 
					"							</div>\r\n" 
					, field.getName(), CommonUtils.getColumnName(field.getName()), field.getRequired(), field.getUiType(), with);
			
		}else{
			code+=String.format(
					"							<div class=\"row form-group\">\r\n" + 
					"								<div class=\"col col-sm-4\">\r\n" + 
					"									<label class=\"control-label mb-1 mw100\">%2$s</label>	\r\n" + 
					"								</div>\r\n" + 
					"									<div class=\"col col-md-6\">\r\n" + 
					"									<select id=\"%1$s\" name=\"cmbType\" %3$s class=\"mw160 input-sm form-control-sm form-control\">\r\n" + 
					"									</select>\r\n" + 
					"								</div>\r\n" + 
					"							</div>\r\n",field.getName(), CommonUtils.getColumnName(field.getName()),field.getRequired());
		}
		return code;
	}

	protected String generateTable(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}

	protected String generateSingleTable(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO, String joinEntityName) {
		
		return String.format(
			
				"							<div class=\"mw150 mh350\" id=\"divtbl%1$s\">\r\n"
				+ "								<table id=\"%2$ss\" class=\"display\">\r\n"
				+ "									<thead>\r\n" + "										<tr>\r\n"
				+ "											<th class=\"pr-2\" width=\"10%%\"><input\r\n"
				+ "												type=\"checkbox\" id=\"checkAll%1$s\"/></th>\r\n"
				+ "											%3$s\r\n" + "										</tr>\r\n"
				+ "									</thead>\r\n" 
				+ "									<tbody>\r\n"
				+ "									</tbody>\r\n"
				+ "								</table>\r\n" 
				+ "							</div>\r\n",
				joinEntityName, CommonUtils.getLowerCaseFirstChar(joinEntityName),
				this.generateTableColumn(omnibusDTO, joinEntityName));
	}
	
	private String generateTableColumn(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO, String joinEntityName) {
		String code = "";
		String[] listTitle = omnibusDTO.getSharedDTO().getEntityByName(joinEntityName).getTitleField().split(",");
		if (listTitle.length > 1) {
			for (int i = 0; i < listTitle.length; i++) {
				code += String.format("													<th class=\"pr-2\">%1$s</th>\r\n",
						CommonUtils.getColumnName(listTitle[i]));
			}
		} else {
			return String.format("<th class=\"pr-2\" width=\"90%%\">All %1$s</th>\r\n", joinEntityName);
		}
		return code;
	}
	
}
