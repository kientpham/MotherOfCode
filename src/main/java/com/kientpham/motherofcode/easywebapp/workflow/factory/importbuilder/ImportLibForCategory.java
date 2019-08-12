package com.kientpham.motherofcode.easywebapp.workflow.factory.importbuilder;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.easywebapp.workflow.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.workflow.factory.common.JavaConst;

public class ImportLibForCategory implements ImportLibInterface{

	@Override
	public String importForRepository(TransactionModel transaction) {		
		return JavaConst.SORT;
	}

	@Override
	public String importForEntity(TransactionModel transaction) {	
		return "";
	}

	@Override
	public String importForDBGateway(TransactionModel transaction) {
		return "";
	}

	@Override
	public String importForDBGatewayInterface(TransactionModel transaction) {
		return "";
	}

	@Override
	public String importForBusinessObject(TransactionModel transaction) {
		return JavaConst.ARRAYLIST + JavaConst.HASHMAP + JavaConst.LIST+JavaConst.MAP;
	}

	@Override
	public String importForServiceInterface(TransactionModel transaction) {		
		return JavaConst.MAP;
	}

	@Override
	public String importForServiceClass(TransactionModel transaction) {		
		return JavaConst.MAP +JavaConst.HASHMAP;
	}
}
