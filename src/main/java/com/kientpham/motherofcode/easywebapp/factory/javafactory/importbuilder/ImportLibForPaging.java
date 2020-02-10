package com.kientpham.motherofcode.easywebapp.factory.javafactory.importbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaCommon;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public class ImportLibForPaging implements ImportLibInterface {

	private String importForListPresenter(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.HASHMAP + JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getTablePage())
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getDataTablePresenter())
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPaginationCriteria())
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingInput())
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingOutput());
	}

	@Override
	public String importForController(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaCommon.importDomain("org.springframework.web.bind.annotation.ResponseBody")
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getTablePage())
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPaginationCriteria());
	}

	@Override
	public String importForRepositoryPaging(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.PAGE + JavaConst.PAGEABLE + JavaConst.REPOSITORY + JavaConst.QUERY + JavaConst.PARAM
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO()
						.getFullDomainDTO(omnibusDTO.getTransaction().getEntity().getName()).getEntityDomain());
	}

	@Override
	public String importForDBGatewayInterface(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return getPageableCommon(omnibusDTO);
	}

	@Override
	public String importForDBGateway(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.SORT + JavaConst.PAGEREQUEST + JavaCommon.importDomain(omnibusDTO.getSharedDTO()
				.getFullDomainDTO(omnibusDTO.getTransaction().getEntity().getName()).getRepositoryPagingDomain())
				+ getPageableCommon(omnibusDTO);
	}

	@Override
	public String importForBusinessObject(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return getPageableCommon(omnibusDTO);
	}

	@Override
	public String importForReadService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getTablePage())
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPaginationCriteria())
				+ getPageableCommon(omnibusDTO);
	}

	@Override
	public String importForReadServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return importForListPresenter(omnibusDTO) + getPageableCommon(omnibusDTO);
	}

	private String getPageableCommon(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return JavaConst.PAGE + JavaConst.PAGEABLE
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingInput())
				+ JavaCommon.importDomain(omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingOutput());
	}

	@Override
	public String importForEntity(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}

	@Override
	public String importForRepository(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return "";
	}

	@Override
	public String importForEditModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String importForTableModel(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String importForJoinList(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String importForWriteService(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String importForWriteServiceImpl(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		// TODO Auto-generated method stub
		return "";
	}

}
