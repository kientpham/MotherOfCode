package com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder;

import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public class ImportLibForCategory extends ImportLibBuilderBase{

	@Override
	public String importForRepository(TransactionModel transaction) {		
		return super.importForRepository(transaction)+JavaConst.SORT + JavaConst.LIST;
	}

	@Override
	public String importForDBGateway(TransactionModel transaction) {
		return super.importForDBGateway(transaction) + JavaConst.SORT ;
	}
	
	@Override
	public String importForBusinessObject(TransactionModel transaction) {
		return super.importForBusinessObject(transaction) + JavaConst.ARRAYLIST + JavaConst.HASHMAP + JavaConst.MAP;
	}

	@Override
	public String importForServiceInterface(TransactionModel transaction) {		
		return super.importForServiceInterface(transaction) + JavaConst.MAP;
	}

	@Override
	public String importForServiceClass(TransactionModel transaction) {		
		return super.importForServiceClass(transaction) + JavaConst.MAP +JavaConst.HASHMAP;
	}
}
