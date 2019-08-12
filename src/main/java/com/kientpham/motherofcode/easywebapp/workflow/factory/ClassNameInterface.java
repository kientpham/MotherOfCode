package com.kientpham.motherofcode.easywebapp.workflow.factory;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public interface ClassNameInterface {

	public String buildClassForEntity(TransactionModel transaction);
	
	public String buildClassForRepository(TransactionModel transaction);
	
	public String buildClassForDBGateway(TransactionModel transaction);
	
	public String buildClassForDBGatewayInterface(TransactionModel transaction);
	
	public String buildClassForBusinessObject(TransactionModel transaction);
	
	public String buildClassForServiceInterface(TransactionModel transaction);
	
	public String buildClassForServiceClass(TransactionModel transaction);	
}
