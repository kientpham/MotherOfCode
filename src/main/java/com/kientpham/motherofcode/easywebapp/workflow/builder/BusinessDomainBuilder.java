package com.kientpham.motherofcode.easywebapp.workflow.builder;

import java.util.ArrayList;
import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeBuilder;
import com.kientpham.motherofcode.utils.Const;
import com.kientpham.motherofcode.utils.PackageUtils;

public class BusinessDomainBuilder implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transactionModel) throws WorkflowException {
		System.out.println(this.buildBusinessClass(transactionModel));
		
	}

	private String buildBusinessClass(TransactionModel transaction) {
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(transaction.getService().getBusinessDomain());
		Entity entity = transaction.getEntity();
		CodeBuilder builder = transaction.getCodeFacade();
		String packageName = PackageUtils.buildDomainName(listDomain);

		String businessDomain = entity.getName() + "Domain";
		builder.buildPackageName(packageName);
		
		builder.importlist();
		builder.importSpringComponent();
		if(entity.getType()!=null && entity.getType().equalsIgnoreCase(Const.CATEGORY)) {
			builder.importListArray();
		}			
		
		if (entity.hasPaging() != null) {
			builder.importSpringData();
			builder.importDomain(transaction.getFullDomainDTO().getPagingInput());
			builder.importDomain(transaction.getFullDomainDTO().getPagingOutput());
		}
		builder.importDomain(transaction.getFullDomainDTO().getEntityDomain());		
		builder.importDomain(transaction.getFullDomainDTO().getDbGatewayInterface());		
		builder.componentAnnotated();
		builder.className(businessDomain);	
		boolean hasPaging=false;
		if (entity.hasPaging() != null) {
			hasPaging=true;
		}
		builder.buildBusinessDomainClass(transaction,hasPaging);
		listDomain.add(businessDomain);
		transaction.getFullDomainDTO().setBussinessDomain(PackageUtils.buildDomainName(listDomain));
		String filePath = transaction.getApplication().getAppPath()+ PackageUtils.buildFilePath(listDomain);		
		PackageUtils.writeToFile(builder.getCode(), filePath);		
		return builder.getCode();
	}
	
}
