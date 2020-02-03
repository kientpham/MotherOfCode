package com.kientpham.motherofcode.easywebapp.factory;

import java.util.ArrayList;
import java.util.List;

import com.kientpham.motherofcode.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.baseworkflow.BaseBuilderPrePost;
import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.easywebapp.workflow.ShareDTO;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;


public abstract class EasyWebAppBaseBuilder implements BaseBuilder<TransactionModel,ShareDTO>, BaseBuilderPrePost<TransactionModel, ShareDTO> {

	private PackageInterface packageBuilder;
	
	private StringBuilder code=new StringBuilder();

	@Override
	public void execute(List<TransactionModel> transactionList, BaseOmnibusDTO<TransactionModel, ShareDTO> omniBusDTO)
			throws WorkflowException {
		this.execute(transactionList.get(0));		
	}
	
	@Override
	public void execute(BaseOmnibusDTO<TransactionModel, ShareDTO> omnibusDTO) throws WorkflowException {
		TransactionModel transaction=omnibusDTO.getTransaction();
		this.execute(transaction);
	}	

	private void execute(TransactionModel transaction) throws WorkflowException {	
		code=new StringBuilder();
		List<String> listDomain = buildListDomain(transaction);
		String packageName = buildPackageName(transaction, listDomain);		
		code.append(packageBuilder.buildPackageName(packageName));	
		String codeBody=generateCode(transaction, packageName);		
		if (codeBody.trim().isEmpty()) return;		
		code.append(codeBody);
		code.append(packageBuilder.buildPackageFooter());		
		createOutputfile(transaction, listDomain);
	}	

	
	private void createOutputfile(TransactionModel transaction, List<String> listDomain) {
		//String filePath = transaction.getApplication().getAppPath()+ packageBuilder.buildFilePath(listDomain);		
		//CommonUtils.writeToFile(code.toString(), filePath);		
		System.out.println(code.toString());
	}

	private String buildPackageName(TransactionModel transaction, List<String> listDomain) {
		packageBuilder=transaction.getCodeFactory().getPackageBuilder();
		String packageName =packageBuilder.buildDomainName(listDomain);
		String outputClassName =buildClassName(transaction) ;
		listDomain.add(outputClassName);		
		saveOutputDomain(transaction, packageBuilder.buildDomainName(listDomain));
		return packageName;
	}
	
	protected String buildClassName(TransactionModel transaction) {
		return transaction.getEntity().getName() + getOutputDomainName();
	}
	
	abstract protected String getBuilderDomain(TransactionModel transaction);

	abstract protected String getOutputDomainName();

	abstract protected void saveOutputDomain(TransactionModel transaction,String outputDomain);
	
	abstract protected String getImportCode(ImportLibInterface importLibBuilder, TransactionModel transaction);

	abstract protected String getClassName(ClassNameInterface classNameBuilder, TransactionModel transaction);
	
	abstract protected String getMethodCode(MethodBuilderInterface methodBuilder, TransactionModel transaction);

	private List<String> buildListDomain(TransactionModel transaction) {
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(getBuilderDomain(transaction));
		return listDomain;
	}

	private String generateCode(TransactionModel transaction, String packageName) {		
			
		StringBuilder codeBody=new StringBuilder();
		for (ImportLibInterface importLibBuilder:transaction.getCodeFactory().getImportLibBuilderList()) {
			codeBody.append(getImportCode(importLibBuilder, transaction));
		}
		for (ClassNameInterface classNameBuilder: transaction.getCodeFactory().getClassNameBuilderList()) {
			codeBody.append(getClassName(classNameBuilder, transaction));
		}		
		for (MethodBuilderInterface methodBuilder:transaction.getCodeFactory().getMethodBuilderList()) {
			codeBody.append(getMethodCode(methodBuilder, transaction));
		}		
		return codeBody.toString();
	}	
}
