package com.kientpham.motherofcode.easywebapp.factory.fixbuilder;

import java.util.HashMap;

import com.kientpham.motherofcode.baseworkflow.BaseBuilder;
import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.baseworkflow.WorkflowException;
import com.kientpham.motherofcode.easywebapp.model.Entity;
import com.kientpham.motherofcode.easywebapp.model.FixDomainDTO;
import com.kientpham.motherofcode.easywebapp.model.FullDomainDTO;
import com.kientpham.motherofcode.easywebapp.model.Service;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;
import com.kientpham.motherofcode.utils.Const;

public class FullDomainBuilder implements BaseBuilder<TransactionModel, SharedDTO> {

	private String commonDomain="";
	
	@Override
	public void execute(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) throws WorkflowException {
		commonDomain=CommonUtils.combineTwoStringWithDot(omniBusDTO.getSharedDTO().getApplication().getDomain(),omniBusDTO.getSharedDTO().getApplication().getCommonDomain());
		this.buildFullFixDomain(omniBusDTO);
		this.buildFullDomainMap(omniBusDTO);
	}

	private void buildFullFixDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) {
		FixDomainDTO fixDomainDTO = new FixDomainDTO();
		//String commonDomain = omniBusDTO.getSharedDTO().getApplication().getCommonDomain();
		fixDomainDTO.setDataTablePresenter(CommonUtils.combineTwoStringWithDot(
				CommonUtils.combineTwoStringWithDot(commonDomain, Const.UTILS), "DataTablePresenter"));
		fixDomainDTO.setColumnClass(getDomainForPagingClasses(commonDomain, "Column"));
		fixDomainDTO.setOrderingCriteria(getDomainForPagingClasses(commonDomain, "OrderingCriteria"));
		fixDomainDTO.setPaginationCriteria(getDomainForPagingClasses(commonDomain, "PaginationCriteria"));
		fixDomainDTO.setPagingInput(getDomainForPagingClasses(commonDomain, "PagingInputDTO"));
		fixDomainDTO.setPagingOutput(getDomainForPagingClasses(commonDomain, "PagingOutputDTO"));
		fixDomainDTO.setSearchCriteria(getDomainForPagingClasses(commonDomain, "SearchCriteria"));
		fixDomainDTO.setTablePage(getDomainForPagingClasses(commonDomain, "TablePage"));
		omniBusDTO.getSharedDTO().setFixDomainDTO(fixDomainDTO);
	}

	private String getDomainForPagingClasses(String domain, String className) {
		return String.format("%1$s.%2$s.%3$s.%4$s", domain, Const.DTO, Const.PAGING, className);
	}

	private void buildFullDomainMap(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO) {
		HashMap<String, FullDomainDTO> fullDomainTable = new HashMap<String, FullDomainDTO>();
		for (Service service : omniBusDTO.getSharedDTO().getApplication().getServices()) {
			for (Entity entity : service.getEntities()) {
				fullDomainTable.put(entity.getName(), this.buildFullDomainDTO(omniBusDTO, service, entity));
				if (entity.getType() != null && entity.getType().equals(Const.ENITY_TYPE_LOOKUP)) {
					omniBusDTO.getSharedDTO().setLookUpEntityName(entity.getName());
				}
			}
		}
		omniBusDTO.getSharedDTO().setFullDomainTable(fullDomainTable);
	}

	private FullDomainDTO buildFullDomainDTO(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO, Service service,
			Entity entity) {
		
		String serviceDomain = getServiceDomain(omniBusDTO, service);
		String entityName = entity.getName();
		
		FullDomainDTO fulldomain = new FullDomainDTO();
		fulldomain.setEntityDomain(this.buildObjectDomain(serviceDomain, entityName, ""));
		fulldomain.setRepositoryDomain(
				this.buildObjectDomain(serviceDomain, Const.DBGATEWAYIMPL, entityName, "Repository"));
		fulldomain.setRepositoryPagingDomain(
				this.buildObjectDomain(serviceDomain, Const.DBGATEWAYIMPL, entityName, "RepositoryPaging"));
		fulldomain.setDbGateway(
				this.buildObjectDomain(serviceDomain, entityName, "DBGateway"));
		fulldomain.setDbGatewayImpl(
				this.buildObjectDomain(serviceDomain, Const.DBGATEWAYIMPL, entityName, "DBGatewayImp"));
		fulldomain.setBussinessDomain(
				this.buildObjectDomain(serviceDomain, entityName, "Domain"));
		fulldomain.setReadService(
				this.buildObjectDomain(serviceDomain, entityName, "ReadService"));
		fulldomain.setReadServiceImpl(
				this.buildObjectDomain(serviceDomain, Const.SERVICEIMPL, entityName, "ReadServiceImpl"));
		fulldomain.setWriteService(
				this.buildObjectDomain(serviceDomain, entityName, "WriteService"));
		fulldomain.setWriteServiceImpl(
				this.buildObjectDomain(serviceDomain, Const.SERVICEIMPL, entityName, "WriteServiceImpl"));
		fulldomain.setControllerDomain(this.buildObjectDomain(serviceDomain, entityName, "Controller"));
		
		//String commonDomain = CommonUtils.combineTwoStringWithDot(omniBusDTO.getSharedDTO().getApplication().getDomain(),omniBusDTO.getSharedDTO().getApplication().getCommonDomain());
		String commonDomainClient=CommonUtils.combineTwoStringWithDot(commonDomain, service.getName().toLowerCase()+"client");
		String commonDTO=CommonUtils.combineTwoStringWithDot(commonDomainClient, Const.DTO);
		fulldomain.setEditModelDomain(this.getDomainForCommon(commonDTO, entityName, "EditDTO"));
		fulldomain.setTableModelDomain(this.getDomainForCommon(commonDTO, entityName, "TableDTO"));
		fulldomain.setJoinListDomain(this.getDomainForCommon(commonDTO, entityName, "JoinListDTO"));
		
		System.out.println(fulldomain.toString());
		return fulldomain;
	}
	
	private String getServiceDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omniBusDTO, Service service) {
		String serviceName = (omniBusDTO.getSharedDTO().getApplication().getServices().size() > 1) ? service.getName()
				: "";
		String serviceDomain = CommonUtils
				.combineTwoStringWithDot(omniBusDTO.getSharedDTO().getApplication().getDomain(), serviceName);
		return serviceDomain;
	}
	
	private String buildObjectDomain(String serviceDomain, String entityName, String objectName) {
		String entityDomain = CommonUtils.combineTwoStringWithDot(serviceDomain,entityName.toLowerCase());		
		return CommonUtils.combineTwoStringWithDot(entityDomain, CommonUtils.combineTwoString(entityName, objectName));
	}

	private String buildObjectDomain(String serviceDomain, String subDomain, String entityName, String objectName) {
		String entityDomain = CommonUtils.combineTwoStringWithDot(serviceDomain,entityName.toLowerCase());
		entityDomain =CommonUtils.combineTwoStringWithDot(entityDomain,subDomain);
		return CommonUtils.combineTwoStringWithDot(entityDomain, CommonUtils.combineTwoString(entityName, objectName));
	}

	private String getDomainForCommon(String commonDomain, String entityName, String className) {
		return String.format("%1$s.%2$s%3$s", commonDomain, entityName,className);
	}
}
