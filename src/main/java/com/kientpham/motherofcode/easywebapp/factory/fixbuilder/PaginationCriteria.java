package com.kientpham.motherofcode.easywebapp.factory.fixbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.FixClassBaseBuilder;
import com.kientpham.motherofcode.easywebapp.factory.FixClassInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public class PaginationCriteria extends FixClassBaseBuilder {

//	@Override
//	protected String getBuilderDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
//		return super.getDomainForPagingClasses(omnibusDTO);
//	}
//
//	@Override
//	protected String buildClassName(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
//		return "PaginationCriteria";
//	}
//
//	@Override
//	protected void saveOutputDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO, String outputDomain) {
//		omnibusDTO.getSharedDTO().getFixDomainDTO().setPaginationCriteria(outputDomain);
//	}

	@Override
	protected String buildCodeBody(FixClassInterface fixClassBuilder,
			BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return fixClassBuilder.buildPaginationCriteria(omnibusDTO);
	}

	@Override
	protected String getDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return omnibusDTO.getSharedDTO().getFixDomainDTO().getPaginationCriteria();
	}

}
