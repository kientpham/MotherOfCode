package com.kientpham.motherofcode.easywebapp.workflow;

import com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory.BusinessDomainBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory.DBGatewayBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory.DBGatewayInterfaceBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory.EntityBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory.RepositoryBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory.ServiceBuilder;
import com.kientpham.motherofcode.easywebapp.workflow.factory.oldfactory.ServiceInterfaceBuilder;
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
