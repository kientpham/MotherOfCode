package com.kientpham.motherofcode.easywebapp.workflow;

import com.kientpham.motherofcode.easywebapp.workflow.builder.BusinessDomainBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.builder.DBGatewayBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.builder.DBGatewayInterfaceBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.builder.EntityBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.builder.RepositoryBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.builder.ServiceBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.builder.ServiceInterfaceBuilder;
import com.kientpham.motherofcode.mainfactory.baseworkflow.AbstractFactory;
import com.kientpham.motherofcode.mainfactory.baseworkflow.MasterWorkflow;

public class EasyWebAppManager extends AbstractFactory<TransactionModel> {


	private EntityBuilder entityBuilder=new EntityBuilder();

	private RepositoryBuilder repositoryBuilder=new RepositoryBuilder();
	
	private DBGatewayInterfaceBuilder dbGatewayInterfaceBuilder=new DBGatewayInterfaceBuilder(); 
	
	private DBGatewayBuilder dbGatewayBuilder=new DBGatewayBuilder();
	
	private BusinessDomainBuilder businessBuilder=new BusinessDomainBuilder();
	
	private ServiceInterfaceBuilder serviceInterfaceBuilder=new ServiceInterfaceBuilder();
	
	private ServiceBuilder serviceBuilder=new ServiceBuilder();
	
	@Override
	protected MasterWorkflow<TransactionModel> initiateWorkflow() {
		transactionManager=new TransactionManager();
		workflow=new MasterWorkflow<TransactionModel>();
		workflow.setBaseTransactionManager(transactionManager);
		workflow.setFirstBuilder(entityBuilder);		
		workflow.setNextBuilder(repositoryBuilder);
		workflow.setNextBuilder(dbGatewayInterfaceBuilder);
		workflow.setNextBuilder(dbGatewayBuilder);
		workflow.setNextBuilder(businessBuilder);
		workflow.setNextBuilder(serviceInterfaceBuilder);
		workflow.setNextBuilder(serviceBuilder);
		return workflow;
	}
}
