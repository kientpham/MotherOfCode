package com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory;

import java.util.ArrayList;
import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeBuilder;
import com.kientpham.motherofcode.utils.PackageUtils;

public class DBGatewayBuilder implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transactionModel) throws WorkflowException {
		System.out.println(this.buildRepositoryClass(transactionModel));

	}

	private String buildRepositoryClass(TransactionModel transaction) {
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(transaction.getService().getDbgatewayDomain());
		Entity entity = transaction.getEntity();
		CodeBuilder builder = transaction.getCodeFacade();
		String packageName = PackageUtils.buildDomainName(listDomain);

		String dbGatewayImpName = entity.getName() + "DBGatewayImp";
		builder.buildPackageName(packageName);
		builder.importlist();		
		builder.importSort();
		builder.importSpringComponent();
		if (entity.hasPaging() != null) {
			builder.importSpringData();
			builder.importSpringPageRequest();
			builder.importDomain(transaction.getFullDomainDTO().getRepositoryPagingDomain());
			builder.importDomain(transaction.getFullDomainDTO().getPagingInput());
			builder.importDomain(transaction.getFullDomainDTO().getPagingOutput());
		}
		builder.importDomain(transaction.getFullDomainDTO().getEntityDomain());
		builder.importDomain(transaction.getFullDomainDTO().getRepositoryDomain());
		builder.importDomain(transaction.getFullDomainDTO().getDbGatewayInterface());
		String dbGateway = PackageUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getDbGatewayInterface());
		builder.componentAnnotated();
		builder.classNameImplements(dbGatewayImpName, dbGateway);
		boolean hasPaging=false;
		if (entity.hasPaging() != null) {
			hasPaging=true;
		}
		builder.buildDBGatewayClass(transaction,hasPaging);
		listDomain.add(dbGatewayImpName);		
		transaction.getFullDomainDTO().setRepositoryDomain(PackageUtils.buildDomainName(listDomain));
		String filePath = transaction.getApplication().getAppPath()+ PackageUtils.buildFilePath(listDomain);		
		PackageUtils.writeToFile(builder.getCode(), filePath);
		return builder.getCode();
	}
	
}
