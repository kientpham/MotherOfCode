package com.kientpham.motherofcode.easywebapp.factory.fixbuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.basebuilder.FixClassBaseBuilder;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.FixClassInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public class PagingOutputBuilder extends FixClassBaseBuilder {

//	@Override
//	protected String getBuilderDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
//		return super.getDomainForPagingClasses(omnibusDTO);
//	}
//	
//	@Override
//	protected String buildClassName(BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO) {
//		return "PagingOutputDTO";
//	}
//
//	@Override
//	protected void saveOutputDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO, String outputDomain) {
//		omnibusDTO.getSharedDTO().getFixDomainDTO().setPagingOutput(outputDomain);
//	}

	@Override
	protected String buildCodeBody(FixClassInterface fixClassBuilder,
			BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {		
		return fixClassBuilder.buildPagingOutput(omnibusDTO);
	}

	@Override
	protected String getDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return omnibusDTO.getSharedDTO().getFixDomainDTO().getPagingOutput();
	}

}
