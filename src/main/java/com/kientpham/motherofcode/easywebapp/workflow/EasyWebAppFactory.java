package com.kientpham.motherofcode.easywebapp.workflow;

import com.kientpham.motherofcode.baseworkflow.AbstractFactory;
import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.baseworkflow.MasterWorkflow;
import com.kientpham.motherofcode.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.builder.BusinessDomainBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.builder.CrudRepositoryBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.builder.DBGatewayImpBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.builder.DBGatewayInterfaceBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.builder.EntityBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.builder.PagingInputBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.builder.PagingOutputBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.builder.RepositoryBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.builder.ServiceImpBuilder;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.builder.ServiceInterfaceBuilder;


public class EasyWebAppFactory extends AbstractFactory<TransactionModel, ShareDTO> {

	private TransactionManager transactionManager=new TransactionManager();;
	
	private EntityBuilder entityBuilder=new EntityBuilder();
	
	private PagingInputBuilder pagingInputBuilder = new PagingInputBuilder();
	
	private PagingOutputBuilder pagingOutputBuilder = new PagingOutputBuilder();
	
	private CrudRepositoryBuilder crudRepositoryBuilder =new CrudRepositoryBuilder();
	
	private RepositoryBuilder repositoryBuilder=new RepositoryBuilder();
	
	private DBGatewayInterfaceBuilder dbGatewayInterfaceBuilder =new DBGatewayInterfaceBuilder();
	
	private DBGatewayImpBuilder dBGatewayImpBuilder=new DBGatewayImpBuilder();
	
	private BusinessDomainBuilder businessDomainBuilder= new BusinessDomainBuilder();
	
	private ServiceInterfaceBuilder serviceInterfaceBuilder=new ServiceInterfaceBuilder();
	
	private ServiceImpBuilder serviceImpBuilder=new ServiceImpBuilder();
	
	@Override
	protected BaseOmnibusDTO<TransactionModel, ShareDTO> initiateBaseOmnibusDTO() throws WorkflowException {
		BaseOmnibusDTO<TransactionModel, ShareDTO> omnibusDTO=new BaseOmnibusDTO<TransactionModel, ShareDTO>();  
		ShareDTO sharedDTO=new ShareDTO();		
		omnibusDTO.setSharedDTO(sharedDTO);		
		return omnibusDTO;
	}

	@Override
	protected MasterWorkflow<TransactionModel, ShareDTO> initiateWorkflow() {
		
		workflow=new MasterWorkflow<TransactionModel, ShareDTO>();
		workflow.setBaseTransactionManager(transactionManager);
		workflow.setPreExecuteBuilder(pagingInputBuilder);
		workflow.setPreExecuteBuilder(pagingOutputBuilder);
		
		workflow.setFirstBuilder(entityBuilder);				
		workflow.setNextBuilder(crudRepositoryBuilder);
		workflow.setNextBuilder(repositoryBuilder);
		workflow.setNextBuilder(dbGatewayInterfaceBuilder);
		workflow.setNextBuilder(dBGatewayImpBuilder);
		workflow.setNextBuilder(businessDomainBuilder);
		workflow.setNextBuilder(serviceInterfaceBuilder);
		workflow.setNextBuilder(serviceImpBuilder);
		return workflow;
	}

}
