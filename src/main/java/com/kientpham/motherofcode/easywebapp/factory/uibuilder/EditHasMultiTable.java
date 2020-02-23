package com.kientpham.motherofcode.easywebapp.factory.uibuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.HtmlEditPageBase;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public class EditHasMultiTable extends HtmlEditPageBase {

	@Override
	public String buildHeaderForEdit(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "	<link rel=\"stylesheet\" type=\"text/css\" href=\"vendor/responsive-multipurpose-tabs/animate.css\" /> \r\n"
				+ "    <link rel=\"stylesheet\" type=\"text/css\" href=\"vendor/responsive-multipurpose-tabs/style1.css\" />  \r\n"
				+ "	<script type=\"text/javascript\" src=\"vendor/responsive-multipurpose-tabs/jquery.multipurpose_tabcontent.js\"></script>\r\n"
				+ "		<script type=\"text/javascript\" >\r\n" + "		$(document).ready(function () {\r\n"
				+ "			$(\".first_tab\").champ();\r\n" + "	\r\n" + "		});\r\n" + "	</script> ";
	}

	@Override
	protected String generateTable(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code = String.format(
				"					\r\n<div class=\"row\">\r\n"
						+ "						<div class=\"tab_wrapper first_tab\">\r\n"
						+ "								%1$s\r\n" 
						+ "								%2$s\r\n"
						+ "				        </div>\r\n" + "					</div>\r\n",
				this.generateTabs(omnibusDTO), this.generateTables(omnibusDTO));

		return code;
	}

	private String generateTabs(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code="";
		boolean isFirstTab=true;		
		if (omnibusDTO.getTransaction().getEntity().hasJoinTable()){
			for(JoinTable joinTable:omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY) || joinTable.getRelation().equals(JavaConst.ONETOMANY)) {
					if (isFirstTab) {
						isFirstTab=false;
						code+=String.format("				                <li class=\"active\">%1$ss</li>\r\n", joinTable.getType());						
					}else {
						code+=String.format("				                <li>%1$ss</li>\r\n", joinTable.getType());
					}
				}
			}
		}
		return String.format(
						"				            <ul class=\"tab_list\">\r\n"+
						"								%1$s\r\n"+
						"							</ul>\r\n",code);
	}

	private String generateTables(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String code="";		
		boolean isFirstTab=true;	
		String active="active";
		if (omnibusDTO.getTransaction().getEntity().hasJoinTable()){
			for(JoinTable joinTable:omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				if (joinTable.getRelation().equals(JavaConst.MANYTOMANY) || joinTable.getRelation().equals(JavaConst.ONETOMANY)) {
					if (isFirstTab) {
						isFirstTab=false;									
					}else {
						active="";
					}
					code+=String.format(
							"				                <div class=\"tab_content %1$s\">\r\n"+
							"									%2$s\r\n"+	
							"								</div>\r\n", active, super.generateSingleTable(omnibusDTO, joinTable.getType()));

				}
			}
		}		
		return String.format(
				"				            <div class=\"content_wrapper\">"+
				"								%1$s"+
				"							</div>\r\n", code);
	}
}
