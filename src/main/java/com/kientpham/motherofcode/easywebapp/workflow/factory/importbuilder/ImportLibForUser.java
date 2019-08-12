package com.kientpham.motherofcode.easywebapp.workflow.factory.importbuilder;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.easywebapp.workflow.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.workflow.factory.common.JavaConst;
import com.kientpham.motherofcode.easywebapp.workflow.factory.common.JavaUtils;
import com.kientpham.motherofcode.utils.Const;

public class ImportLibForUser implements ImportLibInterface {

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
		return "";
	}

	@Override
	public String importForDBGateway(TransactionModel transaction) {
		return "";
	}

	@Override
	public String importForBusinessObject(TransactionModel transaction) {
		return "";
	}

	@Override
	public String importForServiceInterface(TransactionModel transaction) {
		return "";
	}

	@Override
	public String importForServiceClass(TransactionModel transaction) {		
		return JavaConst.ARRAYLIST + JavaConst.HASHSET + JavaConst.ARRAYS + JavaConst.SET
				+JavaUtils.importDomain(transaction.getFullDomainDTO().getEntityDomain()+"."+Const.GROUP)
				+JavaUtils.importDomain(transaction.getFullDomainDTO().getEntityDomain()+"."+Const.PERMISSION);
	}

}
