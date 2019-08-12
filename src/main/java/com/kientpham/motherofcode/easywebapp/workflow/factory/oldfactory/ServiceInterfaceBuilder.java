package com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory;

import java.util.ArrayList;
import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeBuilder;
import com.kientpham.motherofcode.utils.Const;
import com.kientpham.motherofcode.utils.PackageUtils;

public class ServiceInterfaceBuilder implements BaseBuilder<TransactionModel>{

	@Override
	public void execute(TransactionModel transactionModel) throws WorkflowException {
		System.out.println(this.buildServiceInterface(transactionModel));		
	}
	
	private String buildServiceInterface(TransactionModel transaction) {
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(transaction.getService().getServiceDomain());
		Entity entity = transaction.getEntity();
		CodeBuilder builder = transaction.getCodeFacade();
		String packageName = PackageUtils.buildDomainName(listDomain);

		String serviceName = entity.getName() + "Service";
		builder.buildPackageName(packageName);
		builder.importlist();
		if(entity.getType()!=null && entity.getType().equalsIgnoreCase(Const.CATEGORY)) {
			builder.importListArray();
		}	
		if (entity.hasPaging() != null) {
			builder.importSpringPageble();
			builder.importDomain(transaction.getFullDomainDTO().getPagingInput());
			builder.importDomain(transaction.getFullDomainDTO().getPagingOutput());
		}
		builder.importDomain(transaction.getFullDomainDTO().getEntityDomain());

		builder.interfaceName(serviceName);
		boolean hasPaging=false;
		if (entity.hasPaging() != null) {
			hasPaging=true;
		}
		builder.buildServiceInterface(transaction,hasPaging);

		listDomain.add(serviceName);
		String filePath = transaction.getApplication().getAppPath()+ PackageUtils.buildFilePath(listDomain);		
		PackageUtils.writeToFile(builder.getCode(), filePath);
		transaction.getFullDomainDTO().setServiceDomain(PackageUtils.buildDomainName(listDomain));
		return builder.getCode();
	}


}
