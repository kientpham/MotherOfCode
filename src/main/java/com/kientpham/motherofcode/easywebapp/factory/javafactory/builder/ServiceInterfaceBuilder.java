package com.kientpham.motherofcode.easywebapp.factory.javafactory.builder;

import com.kientpham.motherofcode.easywebapp.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.EasyWebAppBaseBuilder;
import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public class ServiceInterfaceBuilder extends EasyWebAppBaseBuilder{

	@Override
	protected String getBuilderDomain(TransactionModel transaction) {		
		return transaction.getService().getServiceDomain();
	}

	@Override
	protected String getOutputDomainName() {
		return "Service";
	}

	@Override
	protected void saveOutputDomain(TransactionModel transaction, String outputDomain) {
		transaction.getFullDomainDTO().setServiceInterfaceDomain(outputDomain);		
	}

	@Override
	protected String getImportCode(ImportLibInterface importLibBuilder, TransactionModel transaction) {
		return importLibBuilder.importForServiceInterface(transaction);
	}

	@Override
	protected String getClassName(ClassNameInterface classNameBuilder, TransactionModel transaction) {	
		return classNameBuilder.buildClassForServiceInterface(transaction);
	}

	@Override
	protected String getMethodCode(MethodBuilderInterface methodBuilder, TransactionModel transaction) {
		return methodBuilder.buildMethodForServiceInterface(transaction);
	}

}
