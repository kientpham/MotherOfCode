package com.kientpham.motherofcode.easywebapp.factory;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public interface MethodBuilderInterface {
	
	public String buildMethodForPagingInput(TransactionModel transaction);
	
	public String buildMethodForPagingOutput(TransactionModel transaction);

	public String buildMethodForEntity(TransactionModel transaction);
	
	public String buildMethodForRepository(TransactionModel transaction);
	
	public String buildMethodForRepositoryPaging(TransactionModel transaction);
	
	public String buildMethodForDBGatewayInterface(TransactionModel transaction);
	
	public String buildMethodForDBGateway(TransactionModel transaction);	
	
	public String buildMethodForBusinessObject(TransactionModel transaction);
	
	public String buildMethodForServiceInterface(TransactionModel transaction);
	
	public String buildMethodForServiceClass(TransactionModel transaction);	
	
	
}
