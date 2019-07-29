package com.kientpham.motherofcode.mainfactory.codefactory;

import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public interface MethodConstruction {

	String defaulConstructor(String className);

	String searchEntity(Entity entity);

	String buildDBGateway(TransactionModel transaction, boolean hasPaging);

	String buildDBGatewayInterface(TransactionModel transaction,boolean hasPaging);

	String buildBusinessObject(TransactionModel transaction, boolean hasPaging);

	String buildServiceInterface(TransactionModel transaction, boolean hasPaging);

	String buildServiceClass(TransactionModel transaction, boolean hasPaging);

	String buildCategoryRepo(TransactionModel transaction);
	
}
