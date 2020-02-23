package com.kientpham.motherofcode.easywebapp.factory.basebuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.FixClassInterface;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.Const;

public abstract class FixClassBaseBuilder extends AbstractBuilder{
	
	abstract protected String buildCodeBody(FixClassInterface fixClassBuilder,BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO);
	
	@Override
	protected String generateCode(BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO) {		
		FixClassInterface fixClassBuilder=omnibusDTO.getSharedDTO().getCodeFactory().getFixClassBuilder();
		StringBuilder codeBody=new StringBuilder();
		codeBody.append(buildCodeBody(fixClassBuilder,omnibusDTO));
		return codeBody.toString();
	}		
	
	protected String getDomainForPagingClasses(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return String.format("%1$s.%2$s.%3$s", omnibusDTO.getSharedDTO().getApplication().getCommonDomain(),
				Const.DTO, Const.PAGING);
	}
	

}
