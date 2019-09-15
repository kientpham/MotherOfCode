package com.kientpham.motherofcode.easywebapp.factory.javafactory.builder;

import com.kientpham.motherofcode.easywebapp.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.EasyWebAppBaseBuilder;
import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public class DBGatewayInterfaceBuilder extends EasyWebAppBaseBuilder{

	@Override
	protected String getBuilderDomain(TransactionModel transaction) {		
		return transaction.getService().getDbgatewayDomain();
	}

	@Override
	protected String getOutputDomainName() {
		return "DBGateway";
	}

	@Override
	protected void saveOutputDomain(TransactionModel transaction, String outputDomain) {
		transaction.getFullDomainDTO().setDbGatewayInterface(outputDomain);		
	}

	@Override
	protected String getImportCode(ImportLibInterface importLibBuilder, TransactionModel transaction) {
		return importLibBuilder.importForDBGatewayInterface(transaction);
	}

	@Override
	protected String getClassName(ClassNameInterface classNameBuilder, TransactionModel transaction) {	
		return classNameBuilder.buildClassForDBGatewayInterface(transaction);
	}

	@Override
	protected String getMethodCode(MethodBuilderInterface methodBuilder, TransactionModel transaction) {
		return methodBuilder.buildMethodForDBGatewayInterface(transaction);
	}

}
