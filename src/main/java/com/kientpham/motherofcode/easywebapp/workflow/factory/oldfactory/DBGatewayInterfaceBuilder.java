package com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory;

import java.util.ArrayList;
import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeBuilder;
import com.kientpham.motherofcode.utils.PackageUtils;

public class DBGatewayInterfaceBuilder implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transactionModel) throws WorkflowException {
		System.out.println(this.buildDbGatewayInterface(transactionModel));

	}

	private String buildDbGatewayInterface(TransactionModel transaction) {
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(transaction.getService().getDbgatewayDomain());
		Entity entity = transaction.getEntity();
		CodeBuilder builder = transaction.getCodeFacade();
		String packageName = PackageUtils.buildDomainName(listDomain);

		String dbGatewayName = entity.getName() + "DBGateway";
		builder.buildPackageName(packageName);
		builder.importlist();
		builder.importSort();
		if (entity.hasPaging() != null) {
			builder.importSpringPageble();
			builder.importDomain(transaction.getFullDomainDTO().getPagingInput());
			builder.importDomain(transaction.getFullDomainDTO().getPagingOutput());
		}
		builder.importDomain(transaction.getFullDomainDTO().getEntityDomain());

		builder.interfaceName(dbGatewayName);
		boolean hasPaging=false;
		if (entity.hasPaging() != null) {
			hasPaging=true;
		}
		builder.buildDBGatewayInterface(transaction,hasPaging);

		listDomain.add(dbGatewayName);
		String filePath = transaction.getApplication().getAppPath()+ PackageUtils.buildFilePath(listDomain);		
		PackageUtils.writeToFile(builder.getCode(), filePath);
		transaction.getFullDomainDTO().setDbGatewayInterface(PackageUtils.buildDomainName(listDomain));
		return builder.getCode();
	}

}
