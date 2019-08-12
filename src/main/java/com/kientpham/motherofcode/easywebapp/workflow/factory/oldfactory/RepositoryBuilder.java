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

public class RepositoryBuilder implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transactionModel) throws WorkflowException {
		
		System.out.println(buildRepositoryClass(transactionModel));		
		if(transactionModel.getEntity().hasPaging()!=null)
			System.out.println(buildPagingRepositoryClass(transactionModel));		

	}

	private String buildRepositoryClass(TransactionModel transaction) {
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(transaction.getService().getRepositoryDomain());
		Entity entity = transaction.getEntity();
		CodeBuilder builder = transaction.getCodeFacade();		
		String packageName = PackageUtils.buildDomainName(listDomain);
		
		String repositoryName = entity.getName() + "Repository";
		builder.buildPackageName(packageName);
		String type=entity.getType();
		if(type!=null&& type.equalsIgnoreCase(Const.CATEGORY)) {
			builder.importSort();
		}
		builder.crudRepository(transaction.getFullDomainDTO().getEntityDomain());
		builder.importlist();
		
		builder.crudRepositoryInterface(repositoryName, entity.getName(),
				entity.getFields().get(0).getType().toString());

		if(type!=null && type.equalsIgnoreCase(Const.CATEGORY)) {
			builder.buildCategoryRepo(transaction);
		}
		listDomain.add(repositoryName);
		String filePath = transaction.getApplication().getAppPath()+ PackageUtils.buildFilePath(listDomain);		
		PackageUtils.writeToFile(builder.getCode(), filePath);
		transaction.getFullDomainDTO().setRepositoryDomain(PackageUtils.buildDomainName(listDomain));				
		return builder.getCode();
	}

	private String buildPagingRepositoryClass(TransactionModel transaction) {
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(transaction.getService().getRepositoryDomain());
		Entity entity = transaction.getEntity();
		CodeBuilder builder = transaction.getCodeFacade();		
		String packageName = PackageUtils.buildDomainName(listDomain);
		
		String repositoryName = entity.getName() + "PagingRepository";		
		
		builder.buildPackageName(packageName);
		builder.pageRepository(transaction.getFullDomainDTO().getEntityDomain());
		builder.repositoryInterface(repositoryName, entity.getName(),
				entity.getFields().get(0).getType().toString());
		builder.searchEntity(entity);

		listDomain.add(repositoryName);
		String filePath = transaction.getApplication().getAppPath()+ PackageUtils.buildFilePath(listDomain);		
		PackageUtils.writeToFile(builder.getCode(), filePath);
		
		transaction.getFullDomainDTO().setRepositoryPagingDomain(PackageUtils.buildDomainName(listDomain));				
		
		return builder.getCode();
	}	
	
}
