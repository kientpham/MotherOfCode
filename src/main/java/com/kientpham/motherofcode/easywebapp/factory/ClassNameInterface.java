package com.kientpham.motherofcode.easywebapp.factory;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public interface ClassNameInterface {

	public String buildClassForPagingInput(TransactionModel transaction);
	
	public String buildClassForPagingOutput(TransactionModel transaction);
	
	public String buildClassForEntity(TransactionModel transaction);
	
	public String buildClassForRepository(TransactionModel transaction);
	
	public String buildClassForRepositoryPaging(TransactionModel transaction);
	
	public String buildClassForDBGateway(TransactionModel transaction);
	
	public String buildClassForDBGatewayInterface(TransactionModel transaction);
	
	public String buildClassForBusinessObject(TransactionModel transaction);
	
	public String buildClassForServiceInterface(TransactionModel transaction);
	
	public String buildClassForServiceClass(TransactionModel transaction);	
}
