package com.kientpham.motherofcode.easywebapp.factory.basebuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public abstract class HtmlBaseBuilder extends AbstractBuilder {

	protected BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO;
	
	@Override
	protected String getDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}

	@Override
	protected String generateCode(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		this.omnibusDTO=omnibusDTO;
		return String.format("<!DOCTYPE html>\r\n" + 
				"<html lang=\"en\">"
				+"<head>\r\n"
				+"	%1$s\r\n"
				+"</head>\r\n"
				+"<body>\r\n"
				+"	%2$s\r\n"
				+"</body>\r\n"
				+ "</html>", this.generateHeader() , this.generateBody());		
	}
	
	protected abstract String generateHeader();
	
	protected abstract String generateBody();	
	
}
