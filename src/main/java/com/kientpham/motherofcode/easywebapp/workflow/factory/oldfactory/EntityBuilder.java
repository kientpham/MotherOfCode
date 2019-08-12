package com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory;

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

public class EntityBuilder implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transaction) throws WorkflowException {
		System.out.println(buildEntityClass(transaction));
	}

	private String buildEntityClass(TransactionModel transaction) {		
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
		transaction.getFullDomainDTO().setEntityDomain(PackageUtils.buildDomainName(listDomain));
		
		String filePath = transaction.getApplication().getAppPath()+ PackageUtils.buildFilePath(listDomain);		
		PackageUtils.writeToFile(builder.getCode(), filePath);
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
