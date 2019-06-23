package com.kientpham.motherofcode.easywebapp.workflow.builder;

import java.util.ArrayList;
import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.CodeBuilder;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.Field;
import com.kientpham.motherofcode.easywebapp.model.JoinTable;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeFacade;
import com.kientpham.motherofcode.utils.Const;

public class EntityBuilder implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transaction) throws WorkflowException {

		transaction.setOutputCode(
				transaction.getOutputCode() + "\r\n" + buildEntityClass(transaction));

	}

	private String buildEntityClass(TransactionModel transaction) {		
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(transaction.getService().getEntityDomain());
		Entity entity=transaction.getEntity();
		StringBuilder sbEntity = new StringBuilder();
		CodeFacade builder = transaction.getCodeFacade();
		//String filePath = builder.getPackageName().buildFilePath(listDomain);
		String domain = builder.buildDomainName(listDomain);
		transaction.getOmnibusDto().setEntityFullDomain(domain);
		
		sbEntity.append(builder.buildPackageName(domain) + Const.newline);
		boolean hasJoinTable = (entity.getJoinTables() != null) ? true : false;
		sbEntity.append(builder.importEntity(hasJoinTable) + "\r\n");
		if (hasJoinTable)
			sbEntity.append(builder.list());
		sbEntity.append(builder.lombokGetterSetter() + "\r\n");
		sbEntity.append(builder.entityAnnotated(entity.getTable()) + "\r\n");
		sbEntity.append(builder.lombokAnnotated());
		sbEntity.append(String.format("%1$s{\r\n\r\n%2$s\r\n}",
				builder.getClassName(entity.getName(), "", ""),
				buildClassContent(entity, builder)));

		return sbEntity.toString();
	}

	private String buildClassContent(Entity entity, CodeFacade builder) {
		StringBuilder sbClassContent = new StringBuilder();
		for (Field field : entity.getFields()) {
			if (field.getIdentity() != null)
				sbClassContent.append(builder.idFieldAnnotated());
			sbClassContent.append(builder.normalFieldAnnoted(field.getColumn()));
			sbClassContent
					.append(builder.getFieldName(field.getType(), field.getName()) + "\r\n");
		}

		if (entity.getJoinTables() != null) {
			for (JoinTable join : entity.getJoinTables()) {
				sbClassContent.append(builder.getJoinFields(join) + "\r\n");
			}
		}
		sbClassContent.append(builder.defaulConstructor(entity.getName()) + "\r\n");

		return sbClassContent.toString();
	}

}
