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

public class ServiceBuilder implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transactionModel) throws WorkflowException {
		System.out.println(this.buildServiceImp(transactionModel));

	}

	private String buildServiceImp(TransactionModel transaction) {
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(transaction.getService().getServiceDomain());
		Entity entity = transaction.getEntity();
		CodeBuilder builder = transaction.getCodeFacade();
		String packageName = PackageUtils.buildDomainName(listDomain);

		String serviceImpName = entity.getName() + "ServiceImp";
		builder.buildPackageName(packageName);
		builder.importlist();
		if(entity.getType()!=null && entity.getType().equalsIgnoreCase(Const.USER)) {
			builder.importListArray();
			builder.importDomain("com.kientpham.webapp.userservice.dto.entity.Group");
			builder.importDomain("com.kientpham.webapp.userservice.dto.entity.Permission");
		}
		if(entity.getType()!=null && entity.getType().equalsIgnoreCase(Const.CATEGORY)) {
			builder.importListArray();
		}	

		
		builder.importSpringComponent();
		
		if (entity.hasPaging() != null) {
			builder.importSpringData();
			builder.importDomain(transaction.getFullDomainDTO().getPagingInput());
			builder.importDomain(transaction.getFullDomainDTO().getPagingOutput());
		}
		builder.importDomain(transaction.getFullDomainDTO().getEntityDomain());
		builder.importDomain(transaction.getFullDomainDTO().getServiceDomain());
		builder.importDomain(transaction.getFullDomainDTO().getBussinessDomain());
		String serviceName = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getServiceDomain());
		builder.componentAnnotated();
		builder.classNameImplements(serviceImpName, serviceName);
		boolean hasPaging=false;
		if (entity.hasPaging() != null) {
			hasPaging=true;
		}
		builder.buildServiceClass(transaction,hasPaging);
		listDomain.add(serviceImpName);
		String filePath = transaction.getApplication().getAppPath()+ PackageUtils.buildFilePath(listDomain);		
		PackageUtils.writeToFile(builder.getCode(), filePath);
		transaction.getFullDomainDTO().setRepositoryDomain(PackageUtils.buildDomainName(listDomain));
		return builder.getCode();
	}
	
}
