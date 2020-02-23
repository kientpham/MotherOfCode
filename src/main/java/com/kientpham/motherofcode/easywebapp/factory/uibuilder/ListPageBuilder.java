package com.kientpham.motherofcode.easywebapp.factory.uibuilder;

import java.util.List;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.basebuilder.HtmlBaseBuilder;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public class ListPageBuilder extends HtmlBaseBuilder {

	@Override
	protected void writeToFileForCode(BaseOmnibusDTO<TransactionModel, SharedDTO> baseOmnibusDTO, StringBuilder code) {
		String path = baseOmnibusDTO.getSharedDTO().getApplication().getAppPath()
				+ "\\src\\main\\resources\\templates\\"
				+ String.format("%1$s-list.html", baseOmnibusDTO.getTransaction().getEntity().getName().toLowerCase());
		CommonUtils.writeToFile(code.toString(), path);
	}

	@Override
	protected String generateHeader() {
		return String.format(
				"    <!-- Required meta tags-->\r\n" + "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n"
				+ "    <meta name=\"description\" content=\"\">\r\n" + "    <meta name=\"keywords\" content=\"\">\r\n"
				+ "    <!-- Title Page-->\r\n" + "    <title>%1$s Management</title>\r\n"
				+ "    <!-- Fontfaces CSS-->\r\n" + "    \r\n"
				+ "    <link href=\"css/font-face.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "    <link href=\"vendor/jquery.dataTables.min.css\" rel=\"stylesheet\" type=\"text/css\">\r\n"
				+ "    \r\n"
				+ "    <link href=\"vendor/font-awesome-4.7/css/font-awesome.min.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "    <link href=\"vendor/font-awesome-5/css/fontawesome-all.min.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "    <link href=\"vendor/mdi-font/css/material-design-iconic-font.min.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "    <!-- Bootstrap CSS-->\r\n"
				+ "    <link href=\"vendor/bootstrap-4.1/bootstrap.min.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "        \r\n" + "    \r\n" + "    <!-- Vendor CSS-->\r\n"
				+ "    <link href=\"vendor/animsition/animsition.min.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "    <link href=\"vendor/bootstrap-progressbar/bootstrap-progressbar-3.3.4.min.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "    <link href=\"vendor/wow/animate.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "    <link href=\"vendor/css-hamburgers/hamburgers.min.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "    <link href=\"vendor/slick/slick.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "    <link href=\"vendor/select2/select2.min.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "    <link href=\"vendor/perfect-scrollbar/perfect-scrollbar.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "	<link href=\"vendor/responsive.dataTables.min.css\" rel=\"stylesheet\" type=\"text/css\">\r\n"
				+ "	\r\n" + "    <!-- Main CSS-->\r\n"
				+ "    <link href=\"css/theme.css\" rel=\"stylesheet\" media=\"all\">\r\n"
				+ "	<link href=\"css/style.css\" rel=\"stylesheet\" media=\"all\" type=\"text/css\">\r\n"
				, omnibusDTO.getTransaction().getEntity().getName());

	}

	@Override
	protected String generateBody() {
		return String.format("%1$s\r\n%2$s", this.generateHtml(),
				this.generateScript());
	}

	private String generateHtml() {
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		return String.format(
				" <div class=\"page-wrapper\"> \r\n" + " <div id=\"headerDiv\"></div> \r\n"
						+ " <!-- PAGE CONTENT-->\r\n" + " <div class=\"page-content--bgf7\"> \r\n" + " \r\n"
						+ " <div id=\"divAlert\" class=\"container\"></div> \r\n" + " <!-- DATA TABLE-->\r\n"
						+ " <section class=\"p-t-20\">\r\n" + " <div class=\"container\">\r\n"
						+ " <div class=\"row\">\r\n" + " <div class=\"col-md-12 top-campaign\"> \r\n"
						+ " <div class=\"table-data__tool\">\r\n" + " <div class=\"table-data__tool-left\">\r\n"
						+ " <div id=\"breadcrumbDiv\" class=\"container breadcrumbDiv\"></div> \r\n" + " </div>\r\n"
						+ " <div class=\"table-data__tool-right\">\r\n"
						+ " <button id=\"btnAdd\" class=\"au-btn au-btn-icon au-btn--green	 au-btn--small\">\r\n"
						+ " <i class=\"zmdi zmdi-plus\"></i>add %2$s</button> \r\n" + " </div>\r\n" + " </div>\r\n"
						+ " <div id=\"divtblResult\" class=\"table-responsive \">\r\n"
						+ " <table id=\"tbl%1$s\" class=\"table table-top-campaign	 table-hover\">\r\n"
						+ " <thead>\r\n" + " <tr>\r\n" + " <th width=\"4%%;\"></th>\r\n" + " %3$s"
						+ " <th data-priority=\"1\" width=\"1&#37;\">Action</th>\r\n" + " </tr>" + " </thead>\r\n"
						+ " <tbody>\r\n" + " </tbody>\r\n" + " </table> \r\n" + " </div>\r\n" + " </div>\r\n"
						+ " </div>\r\n" + " </div>\r\n" + " </section>\r\n" + " <!-- END DATA TABLE--> \r\n"
						+ " <div id=\"divCopyright\" class=\"container\"></div> \r\n" + " </div>\r\n" + " </div>",
				entityName, entityName.toLowerCase(), this.buildTable());
	}

	private String buildTable() {
		List<Field> fields = omnibusDTO.getTransaction().getEntity().getFields();
		String code = "";
		int priority = 1;
		for (Field field : fields) {
			if (field.isShowInTable()) {
				String width = (!field.getWidth().isEmpty()) ? String.format("width=\"%1$s%%\"", field.getWidth()) : "";
				code += String.format(
						"\r\n		<th data-priority=\"%1$s\" %2$s style=\"text-align: left;\" class=\"sorting\">%3$s</th>",
						priority, width, CommonUtils.getColumnName(field.getName()));
				priority++;
			}
		}
		return code;
	}

	private String generateScript() {
		return "<!-- Jquery JS-->\r\n" + "    <script src=\"vendor/jquery-3.2.1.min.js\"></script>\r\n"
				+ "    <!-- Bootstrap JS-->\r\n" + "    \r\n"
				+ "    <script src=\"vendor/bootstrap-4.1/popper.min.js\"></script>\r\n"
				+ "    <script src=\"vendor/bootstrap-4.1/bootstrap.min.js\"></script>\r\n" + "    \r\n"
				+ "    <!-- Vendor JS       -->\r\n" + "    <script src=\"vendor/slick/slick.min.js\"></script>\r\n"
				+ "    <script src=\"vendor/wow/wow.min.js\"></script>\r\n"
				+ "    <script src=\"vendor/animsition/animsition.min.js\"></script>\r\n"
				+ "    <script src=\"vendor/bootstrap-progressbar/bootstrap-progressbar.min.js\"></script>\r\n"
				+ "    <script src=\"vendor/counter-up/jquery.waypoints.min.js\"></script>\r\n"
				+ "    <script src=\"vendor/counter-up/jquery.counterup.min.js\"></script>\r\n"
				+ "    <script src=\"vendor/circle-progress/circle-progress.min.js\"></script>\r\n"
				+ "    <script src=\"vendor/perfect-scrollbar/perfect-scrollbar.js\"></script>\r\n"
				+ "    <script src=\"vendor/chartjs/Chart.bundle.min.js\"></script>\r\n"
				+ "    <script src=\"vendor/select2/select2.min.js\"></script>\r\n" + "    <!-- Main JS-->\r\n"
				+ "    <script type=\"text/javascript\" src=\"js/main.js\"></script>	\r\n"
				+ "	<script type=\"text/javascript\" src=\"vendor/jquery.dataTables.min.js\"></script>\r\n"
				+ "	<script type=\"text/javascript\" src=\"vendor/dataTables.responsive.min.js\"></script>\r\n"
				+ "	<script type=\"text/javascript\" src=\"js/autodatabinding.js\"></script>"
				+ this.buildScript();
	}

	private String buildScript() {
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		return String.format(
				"	<script>		\r\n" + "		var %1$sTableObj;\r\n" + "		var editFormObj;\r\n"
						+ "		$(document).ready(function() {	\r\n" + "				\r\n"
						+ "		 	var divAndHtml = new Array ( );\r\n"
						+ "			divAndHtml[0] = new Array ( 'headerDiv', 'header.html');			\r\n"
						+ "			divAndHtml[1] = new Array ( 'divCopyright', 'copyright.html');		\r\n"
						+ "			divAndHtml[2] = new Array ( 'divAlert', 'alert.html');				\r\n"
						+ "			loadHtml(divAndHtml);\r\n" + "			\r\n"
						+ "			var breadcrumb = new Array ( );\r\n"
						+ "			breadcrumb[0] = new Array ( 'Home', '#');			\r\n"
						+ "			breadcrumb[1] = new Array ( '%2$s Management', '');					\r\n"
						+ "			createBreadcrumb('breadcrumbDiv',breadcrumb);" + "			%3$s;\r\n"
						+ "		});\r\n" + "	</script>",
				entityName.toLowerCase(), entityName,
				this.generateEditFormObj() + this.generateTableObj());

	}

	private String generateEditFormObj() {
		String code = "";
		String serviceName = omnibusDTO.getTransaction().getService().getName();
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String editDTO = CommonUtils
				.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getEditModelDomain())
				.toLowerCase();
		String joinTableArr = "";
		if (omnibusDTO.getTransaction().getEntity().hasJoinTable()) {
			joinTableArr = "[";
			for (JoinTable joinTable : omnibusDTO.getTransaction().getEntity().getJoinTables()) {
				String joinTableName = joinTable.getType();
				String joinListDTO = CommonUtils
						.getObjectNameFromDomain(
								omnibusDTO.getSharedDTO().getFullDomainDTO(joinTableName).getJoinListDomain())
						.toLowerCase();
				int numberOfColumn = omnibusDTO.getSharedDTO().getEntityByName(joinTableName).getNumberOfTitleColumn()
						+ 1;
				code += String.format("\r\n			var table%1$sObj= new TableClass();\r\n"
						+ "			table%1$sObj.type=\"checkbox\";\r\n" + "			table%1$sObj.bFilter=false;\r\n"
						+ "			table%1$sObj.bPaginate=true;\r\n" 
						+ "			table%1$sObj.pageLength=10;\r\n"
						+ "			table%1$sObj.bLengthChange=false;\r\n"
						+ "			table%1$sObj.bInfo=false;\r\n"
						+ "			table%1$sObj.order=1;\r\n" + "			table%1$sObj.tableId=\"#%2$ss\";\r\n"
						+ "			table%1$sObj.getTableData=\"/%3$s/%4$ss/%5$s\";\r\n"
						+ "			table%1$sObj.numberOfColumns=%6$s;\r\n"
						+ "			table%1$sObj.checkAllId=\"#checkAll%1$s\";\r\n\r\n", joinTableName,
						joinTableName.toLowerCase(), serviceName, CommonUtils.getLowerCaseFirstChar(entityName),
						joinListDTO, numberOfColumn);
				joinTableArr += String.format("table%1$sObj,", joinTableName);
			}
			joinTableArr = joinTableArr.substring(0, joinTableArr.length() - 1) + "]";
		}
		code += "			var dropdownListEdit = new Array ( );\r\n";
		int i=0;
		for (Field field : omnibusDTO.getTransaction().getEntity().getFields()) {			
			if (!field.getLookupType().isEmpty()) {
				String nullable = (field.getNullable().isEmpty()) ? "false" : "true";
				code += String.format("			dropdownListEdit[%4$s] = new Array ( '%1$s', '%2$s',%3$s);\r\n",
						field.getName(), field.getLookupType(), nullable,i);
				i++;
			}
		}
		code += String.format(
				"			editFormObj=new EditForm();\r\n" + "			editFormObj.idFieldName=\"%5$s\";\r\n"
						+ "			editFormObj.editForm=\"#edit%1$s\";\r\n"
						+ "			editFormObj.getDropDownValue=\"/shareservice/lookups/lookuptabledto\";\r\n"
						+ "			editFormObj.dropdownList=dropdownListEdit;\r\n"
						+ "			editFormObj.getValuesToBind=\"/%3$s/%2$ss/%4$s\";\r\n"
						+ "			editFormObj.saveDataObject=\"/%3$s/%2$ss/%4$s\";\r\n"
						+ "			editFormObj.editFormHtml=\"%2$s-edit.html\";\r\n"
						+ "			editFormObj.saveButtonId=['#btnSave','%2$sTableObj'];\r\n",						
				entityName, entityName.toLowerCase(), serviceName, editDTO,
				omnibusDTO.getTransaction().getEntity().getIDField().getName(), joinTableArr);
		if (!joinTableArr.isEmpty())
			code+=String.format("			editFormObj.dataTableListObj=%1$s;\r\n",joinTableArr);

		return code;
	}

	private String generateTableObj() {
		String serviceName = omnibusDTO.getTransaction().getService().getName();
		String entityName = omnibusDTO.getTransaction().getEntity().getName();
		String tableDTO=CommonUtils.getObjectNameFromDomain(omnibusDTO.getTransaction().getFullDomainDTO().getTableModelDomain()).toLowerCase();
		int numberOfTableColumn = omnibusDTO.getTransaction().getEntity().getNumberOfTableColumn() + 2;//One for hidden ID + one for Action column
		return String.format("\r\n			%1$sTableObj= new TableClass();\r\n"
				+ "			%1$sTableObj.type=\"edit\";\r\n" + "			%1$sTableObj.bFilter=true;\r\n"
				+ "			%1$sTableObj.bPaginate=true;\r\n" + "			%1$sTableObj.pageLength=25;\r\n"
				+ "			%1$sTableObj.bInfo=true;\r\n" + "			%1$sTableObj.bLengthChange=true;\r\n"
				+ "			%1$sTableObj.bLazyTable=false;	\r\n" + "			%1$sTableObj.tableId=\"#tbl%4$s\";\r\n"
				+ "			%1$sTableObj.numberOfColumns=%3$s;\r\n"
				+ "			%1$sTableObj.getTableData=\"/%2$s/%1$ss/%5$s\";\r\n"
				+ "			%1$sTableObj.order=1;\r\n" + "			%1$sTableObj.deleteController=\"/%2$s/%1$ss/\";\r\n"
				+ "			%1$sTableObj.editFormObj=editFormObj;				\r\n"
				+ "			%1$sTableObj.buttonAddId=\"#btnAdd\";							\r\n"
				+ "			%1$sTableObj.searchPlaceholder=\"Type here to search %1$s . . .\";\r\n"
				+ "				\r\n" + "			%1$sTableObj.loadTable();	", entityName.toLowerCase(),
				serviceName, numberOfTableColumn, entityName,tableDTO);

	}

}
