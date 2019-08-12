package com.kientpham.motherofcode.easywebapp.workflow;

import java.util.ArrayList;
import java.util.List;

import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.utils.PackageUtils;

public abstract class EasyWebAppBaseBuilder implements BaseBuilder<TransactionModel>{
	
	@Override
	public void execute(TransactionModel transaction) throws WorkflowException {				
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(getBuilderDomain(transaction));
		String packageName = PackageUtils.buildDomainName(listDomain);
		String outputDomainName=transaction.getEntity().getName()+getOutputDomainName();
		saveOutputDomain(outputDomainName);
		
	}
	
	abstract protected String getBuilderDomain(TransactionModel transaction);
		
	abstract protected String getOutputDomainName();	
	
	abstract protected void saveOutputDomain(String outputDomain);
	
	abstract protected String getType();

}
