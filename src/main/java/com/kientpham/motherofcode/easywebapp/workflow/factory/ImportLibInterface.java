package com.kientpham.motherofcode.easywebapp.workflow.factory;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public interface ImportLibInterface {
	
	public String importForEntity(TransactionModel transaction);
	
	public String importForRepository(TransactionModel transaction);
	
	public String importForDBGatewayInterface(TransactionModel transaction);
	
	public String importForDBGateway(TransactionModel transaction);

	public String importForBusinessObject(TransactionModel transaction);
	
	public String importForServiceInterface(TransactionModel transaction);
	
	public String importForServiceClass(TransactionModel transaction);	
	
}
