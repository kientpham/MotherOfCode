package com.kientpham.motherofcode.easywebapp.workflow;

import com.kientpham.motherofcode.easywebapp.workflow.builder.EntityBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.builder.InputXMLReader;
import com.kientpham.motherofcode.easywebapp.workflow.builder.LanguageFactory;
import com.kientpham.motherofcode.easywebapp.workflow.builder.RepositoryBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.AbstractFactory;
import com.kientpham.motherofcode.mainfactory.baseworkflow.MasterWorkflow;

public class EasyWebAppManager extends AbstractFactory<TransactionModel> {

	private LanguageFactory languageFactory=new LanguageFactory();
	
	private InputXMLReader inputXMLReader=new InputXMLReader();
	
	private EntityBuilder entityBuilder=new EntityBuilder();

	private RepositoryBuilder repositoryBuilder=new RepositoryBuilder();
	
	@Override
	protected MasterWorkflow<TransactionModel> initiateWorkflow() {
		transactionManager=new TransactionManager();
		workflow=new MasterWorkflow<TransactionModel>();
		workflow.setBaseTransactionManager(transactionManager);
		//workflow.setFirstBuilder(languageFactory);
		//workflow.setNextBuilder(inputXMLReader);
		workflow.setFirstBuilder(entityBuilder);
		workflow.setNextBuilder(repositoryBuilder);
		return workflow;
	}
}
