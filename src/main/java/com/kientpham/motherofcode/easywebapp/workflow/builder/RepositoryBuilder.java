package com.kientpham.motherofcode.easywebapp.workflow.builder;

import java.util.ArrayList;
import java.util.List;

import com.kientpham.motherofcode.easywebapp.model.Application;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.Service;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.mainfactory.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.mainfactory.codefactory.CodeFacade;
import com.kientpham.motherofcode.utils.Const;

public class RepositoryBuilder implements BaseBuilder<TransactionModel> {

	@Override
	public void execute(TransactionModel transactionModel) throws WorkflowException {

		transactionModel.setOutputCode(
				transactionModel.getOutputCode() + "\r\n" + buildRepositoryClass(transactionModel));

	}

	private String buildRepositoryClass(TransactionModel transaction) {
		List<String> listDomain = new ArrayList<String>();
		listDomain.add(transaction.getApplication().getDomain());
		listDomain.add(transaction.getService().getName());
		listDomain.add(transaction.getService().getRepositoryDomain());
		Entity entity=transaction.getEntity();
		StringBuilder sbRepository = new StringBuilder();
		CodeFacade builder = transaction.getCodeFacade();
		String filePath = builder.buildFilePath(listDomain);
		String domain = builder.buildDomainName(listDomain);
		sbRepository.append(builder.buildPackageName(domain) + Const.newline);
		sbRepository.append(
				builder.crudRepository(transaction.getOmnibusDto().getEntityFullDomain()) + "\r\n\r\n");
		sbRepository.append(builder.getRepositoryInterface(entity.getName() + "Repository",
				entity.getName(), entity.getFields().get(0).getType().toString()) + Const.newline);

		return sbRepository.toString();
	}

}
