package com.kientpham.motherofcode.easywebapp.factory.uibuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.basebuilder.HtmlBaseBuilder;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public class EditPageBuilder extends HtmlBaseBuilder{
	
	@Override
	protected void writeToFileForCode(BaseOmnibusDTO<TransactionModel, SharedDTO> baseOmnibusDTO, StringBuilder code) {
		String path = baseOmnibusDTO.getSharedDTO().getApplication().getAppPath()
				+ "\\src\\main\\resources\\static\\"
				+ String.format("%1$s-edit.html", baseOmnibusDTO.getTransaction().getEntity().getName().toLowerCase());
		CommonUtils.writeToFile(code.toString(), path);
	}
	
	@Override
	protected String generateHeader() {
		return omnibusDTO.getTransaction().getCodeFactory().getHtmlPageBuilder().buildHeaderForEdit(omnibusDTO);
	}

	@Override
	protected String generateBody() {
		return omnibusDTO.getTransaction().getCodeFactory().getHtmlPageBuilder().buildBodyForEditPage(omnibusDTO);
	}

}
