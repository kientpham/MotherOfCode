package com.kientpham.motherofcode.easywebapp.workflow.builder;

import java.util.ArrayList;
import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeBuilder;
import com.kientpham.motherofcode.utils.PackageUtils;

public class PagingInputBuilder implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transaction) throws WorkflowException {
		System.out.println(buildPagingInputClass(transaction));
	}

	private String buildPagingInputClass(TransactionModel transaction) {		
		Entity entity=transaction.getEntity();		
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(transaction.getService().getEntityDomain());
		
		CodeBuilder builder = transaction.getCodeFacade();		
		builder.buildPackageName(PackageUtils.buildDomainName(listDomain));
		boolean hasJoinTable = (entity.getJoinTables() != null) ? true : false;
		builder.importEntity(hasJoinTable);		
		if (hasJoinTable)
			builder.importlist();
		builder.lombokGetterSetter();
		builder.entityAnnotated(entity.getTable());
		builder.getsetAnnotated();		
		buildClassContent(entity, builder);
		
		listDomain.add(entity.getName());
		String filePath = PackageUtils.buildFilePath(listDomain);
		transaction.getFullDomainDTO().setEntityDomain(PackageUtils.buildDomainName(listDomain));
		
		return builder.getCode();
	}

	private void buildClassContent(Entity entity, CodeBuilder builder) {	
		
		builder.className(entity.getName());
		for (Field field : entity.getFields()) {
			if (field.getIdentity() != null)
				builder.idFieldAnnotated();
			
			builder.normalFieldAnnoted(field.getColumn());
			builder.getFieldName(field.getType(), field.getName());
		}

		if (entity.getJoinTables() != null) {
			for (JoinTable join : entity.getJoinTables()) {
				builder.getJoinFields(join);
			}
		}
		builder.defaulConstructor(entity.getName());		
	}

}
