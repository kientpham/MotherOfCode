package com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder;

import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public class ImportLibForPaging implements ImportLibInterface {

	@Override
	public String importForEntity(TransactionModel transaction) {
		return "";
	}

	@Override
	public String importForRepository(TransactionModel transaction) {
		return "";
	}
	
	@Override
	public String importForRepositoryPaging(TransactionModel transaction) {
		return JavaConst.PAGE + JavaConst.PAGEABLE +JavaConst.REPOSITORY
				+JavaConst.QUERY + JavaConst.PARAM 
				+ "import "+transaction.getFullDomainDTO().getEntityDomain()+";\r\n";
	}

	@Override
	public String importForDBGatewayInterface(TransactionModel transaction) {
		return getPageableCommon(transaction);
	}

	@Override
	public String importForDBGateway(TransactionModel transaction) {
		return JavaConst.SORT + JavaConst.PAGEREQUEST
				+ "import "+transaction.getFullDomainDTO().getRepositoryPagingDomain()+";\r\n"
				+ getPageableCommon(transaction);
	}

	@Override
	public String importForBusinessObject(TransactionModel transaction) {
		return getPageableCommon(transaction);
	}

	@Override
	public String importForServiceInterface(TransactionModel transaction) {
		return getPageableCommon(transaction);
	}

	@Override
	public String importForServiceClass(TransactionModel transaction) {
		return getPageableCommon(transaction);
	}

	private String getPageableCommon(TransactionModel transaction) {
		return JavaConst.PAGE + JavaConst.PAGEABLE
				+ "import "+transaction.getFullDomainDTO().getPagingInput()+";\r\n"
				+ "import "+transaction.getFullDomainDTO().getPagingOutput()+";\r\n";
	}

	@Override
	public String importForPagingOutput(TransactionModel transaction) {
		return "";
	}

	@Override
	public String importForPagingInput(TransactionModel transaction) {
		return "";
	}

}
