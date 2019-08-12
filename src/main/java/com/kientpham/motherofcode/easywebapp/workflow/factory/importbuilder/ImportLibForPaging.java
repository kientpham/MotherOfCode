package com.kientpham.motherofcode.easywebapp.workflow.factory.importbuilder;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.easywebapp.workflow.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.workflow.factory.common.JavaConst;
import com.kientpham.motherofcode.easywebapp.workflow.factory.common.JavaUtils;

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
	public String importForDBGatewayInterface(TransactionModel transaction) {
		return getPageableCommon(transaction);
	}

	@Override
	public String importForDBGateway(TransactionModel transaction) {
		return JavaConst.SORT + JavaConst.PAGEREQUEST
				+ JavaUtils.importDomain(transaction.getFullDomainDTO().getRepositoryPagingDomain())
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
				+ JavaUtils.importDomain(transaction.getFullDomainDTO().getPagingInput())
				+ JavaUtils.importDomain(transaction.getFullDomainDTO().getPagingOutput());
	}

}
