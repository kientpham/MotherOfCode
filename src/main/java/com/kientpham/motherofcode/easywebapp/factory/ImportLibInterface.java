package com.kientpham.motherofcode.easywebapp.factory;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public interface ImportLibInterface {
	
	public String importForPagingInput(TransactionModel transaction);
	
	public String importForPagingOutput(TransactionModel transaction);
	
	public String importForEntity(TransactionModel transaction);
	
	public String importForRepository(TransactionModel transaction);
	
	public String importForRepositoryPaging(TransactionModel transaction);
	
	public String importForDBGatewayInterface(TransactionModel transaction);
	
	public String importForDBGateway(TransactionModel transaction);

	public String importForBusinessObject(TransactionModel transaction);
	
	public String importForServiceInterface(TransactionModel transaction);
	
	public String importForServiceClass(TransactionModel transaction);	
	
	
	
}
