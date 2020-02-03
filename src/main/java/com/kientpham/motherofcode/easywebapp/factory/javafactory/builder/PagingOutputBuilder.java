package com.kientpham.motherofcode.easywebapp.factory.javafactory.builder;

import java.util.List;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.easywebapp.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.EasyWebAppBaseBuilder;
import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.workflow.ShareDTO;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;

public class PagingOutputBuilder extends EasyWebAppBaseBuilder{

	String outputPageDomain="";
	
	@Override
	public void execute(List<TransactionModel> transactionList, BaseOmnibusDTO<TransactionModel, ShareDTO> omniBusDTO)
			throws WorkflowException {
		super.execute(transactionList, omniBusDTO);
		for (TransactionModel transaction:transactionList){
			transaction.getFullDomainDTO().setPagingOutput(outputPageDomain);
		}
	}
	
	@Override
	protected String getBuilderDomain(TransactionModel transaction) {		
		return transaction.getService().getCommonDomain() +".dto.paging";
	}

	@Override
	protected String buildClassName(TransactionModel transaction) {
		return getOutputDomainName();
	}
	
	@Override
	protected String getOutputDomainName() {
		return "PagingOutputDTO";
	}

	@Override
	protected void saveOutputDomain(TransactionModel transaction, String outputDomain) {
		transaction.getFullDomainDTO().setPagingOutput(outputDomain);	
		outputPageDomain=outputDomain;
	}

	@Override
	protected String getImportCode(ImportLibInterface importLibBuilder, TransactionModel transaction) {
		return importLibBuilder.importForPagingOutput(transaction);
	}

	@Override
	protected String getClassName(ClassNameInterface classNameBuilder, TransactionModel transaction) {	
		return classNameBuilder.buildClassForPagingOutput(transaction);
	}

	@Override
	protected String getMethodCode(MethodBuilderInterface methodBuilder, TransactionModel transaction) {
		return methodBuilder.buildMethodForPagingOutput(transaction);
	}

}
