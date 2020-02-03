package com.kientpham.motherofcode.easywebapp.workflow;

import com.kientpham.motherofcode.baseworkflow.AbstractFactory;
import com.kientpham.motherofcode.baseworkflow.MasterWorkflow;
import com.kientpham.motherofcode.easywebapp.factory.commonbuilder.EditModelBuilder;
import com.kientpham.motherofcode.easywebapp.factory.commonbuilder.JoinListBuilder;
import com.kientpham.motherofcode.easywebapp.factory.commonbuilder.TableModelBuilder;
import com.kientpham.motherofcode.easywebapp.factory.fixbuilder.ColumnBuilder;
import com.kientpham.motherofcode.easywebapp.factory.fixbuilder.DataTablePresenter;
import com.kientpham.motherofcode.easywebapp.factory.fixbuilder.FullDomainBuilder;
import com.kientpham.motherofcode.easywebapp.factory.fixbuilder.OrderingCriteria;
import com.kientpham.motherofcode.easywebapp.factory.fixbuilder.PaginationCriteria;
import com.kientpham.motherofcode.easywebapp.factory.fixbuilder.PagingInputBuilder;
import com.kientpham.motherofcode.easywebapp.factory.fixbuilder.PagingOutputBuilder;
import com.kientpham.motherofcode.easywebapp.factory.fixbuilder.SearchCriteria;
import com.kientpham.motherofcode.easywebapp.factory.fixbuilder.TablePage;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.BusinessDomainBuilder;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.ControllerBuilder;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.CrudRepositoryBuilder;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.DBGatewayImpBuilder;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.DBGatewayInterfaceBuilder;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.EntityBuilder;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.RepositoryBuilder;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.WriteServiceBuilder;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.WriteServiceImplBuilder;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.ReadServiceImplBuilder;
import com.kientpham.motherofcode.easywebapp.factory.servicebuilder.ReadServiceBuilder;
import com.kientpham.motherofcode.easywebapp.factory.uibuilder.EditPresenterBuilder;
import com.kientpham.motherofcode.easywebapp.factory.uibuilder.ListPresenterBuilder;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public class EasyWebAppFactory extends AbstractFactory<TransactionModel, SharedDTO> {

	private FullDomainBuilder fullDomainBuilder=new FullDomainBuilder();
	
	private ColumnBuilder columnBuilder=new ColumnBuilder();
	
	private OrderingCriteria orderingCriteria=new OrderingCriteria();
	
	private PaginationCriteria paginationCriteria=new PaginationCriteria();
	
	private SearchCriteria searchCriteria=new SearchCriteria();
	
	private TablePage tablePage=new TablePage();
	
	private DataTablePresenter dataTablePresenter=new DataTablePresenter();
	
	private PagingInputBuilder pagingInputBuilder = new PagingInputBuilder();
	
	private PagingOutputBuilder pagingOutputBuilder = new PagingOutputBuilder();
	
	private EntityBuilder entityBuilder=new EntityBuilder();
	
	private CrudRepositoryBuilder crudRepositoryBuilder =new CrudRepositoryBuilder();
	
	private RepositoryBuilder repositoryBuilder=new RepositoryBuilder();
	
	private DBGatewayInterfaceBuilder dbGatewayInterfaceBuilder =new DBGatewayInterfaceBuilder();
	
	private DBGatewayImpBuilder dBGatewayImpBuilder=new DBGatewayImpBuilder();
	
	private BusinessDomainBuilder businessDomainBuilder= new BusinessDomainBuilder();
	
	private ReadServiceBuilder readServiceBuilder=new ReadServiceBuilder();
	
	private ReadServiceImplBuilder readServiceImplBuilder=new ReadServiceImplBuilder();
	
	private WriteServiceBuilder writeServiceBuilder=new WriteServiceBuilder();
	
	private WriteServiceImplBuilder writeServiceImplBuilder=new WriteServiceImplBuilder();
	
	private EditModelBuilder editModelBuilder=new EditModelBuilder();	
	
	private TableModelBuilder tableModelBuilder= new TableModelBuilder();
	
	private JoinListBuilder joinListBuilder=new JoinListBuilder();
	
	private EditPresenterBuilder editPresenterBuilder=new EditPresenterBuilder();
	
	private ListPresenterBuilder listPresenterBuilder=new ListPresenterBuilder();
	
	private ControllerBuilder controllerBuilder= new ControllerBuilder();
	
	@Override
	protected MasterWorkflow<TransactionModel, SharedDTO> initiateWorkflow() {
		transactionManager=new TransactionManager();
		workflow=new MasterWorkflow<TransactionModel, SharedDTO>();
		workflow.setBaseTransactionManager(transactionManager);		
		workflow.setPreExecuteBuilder(fullDomainBuilder);
		workflow.setPreExecuteBuilder(pagingInputBuilder);
		workflow.setPreExecuteBuilder(pagingOutputBuilder);
		workflow.setPreExecuteBuilder(columnBuilder);
		workflow.setPreExecuteBuilder(orderingCriteria);
		workflow.setPreExecuteBuilder(paginationCriteria);
		workflow.setPreExecuteBuilder(searchCriteria);
		workflow.setPreExecuteBuilder(tablePage);
		workflow.setPreExecuteBuilder(dataTablePresenter);
		
		workflow.setFirstBuilder(entityBuilder);
		workflow.setNextBuilder(crudRepositoryBuilder);
		workflow.setNextBuilder(repositoryBuilder);
		workflow.setNextBuilder(dbGatewayInterfaceBuilder);
		workflow.setNextBuilder(dBGatewayImpBuilder);
		workflow.setNextBuilder(businessDomainBuilder);
		workflow.setNextBuilder(editModelBuilder);
		workflow.setNextBuilder(tableModelBuilder);
		workflow.setNextBuilder(joinListBuilder);
		workflow.setNextBuilder(readServiceBuilder);
		workflow.setNextBuilder(readServiceImplBuilder);
		workflow.setNextBuilder(writeServiceBuilder);
		workflow.setNextBuilder(writeServiceImplBuilder);

//		workflow.setNextBuilder(editPresenterBuilder);
//		workflow.setNextBuilder(listPresenterBuilder);
		workflow.setNextBuilder(controllerBuilder);
		return workflow;
	}

}
