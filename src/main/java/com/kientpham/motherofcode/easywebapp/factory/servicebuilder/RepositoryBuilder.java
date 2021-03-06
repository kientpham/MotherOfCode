package com.kientpham.motherofcode.easywebapp.factory.servicebuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.basebuilder.EntityBaseBuilder;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public class RepositoryBuilder extends EntityBaseBuilder{
	
	@Override
	protected String getImportCode(ImportLibInterface importLibBuilder, BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return importLibBuilder.importForRepositoryPaging(omnibusDTO);
	}

	@Override
	protected String getClassName(ClassNameInterface classNameBuilder, BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {	
		return classNameBuilder.buildClassForRepositoryPaging(omnibusDTO);
	}

	@Override
	protected String getMethodCode(MethodBuilderInterface methodBuilder, BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return methodBuilder.buildMethodForRepositoryPaging(omnibusDTO);
	}

	@Override
	protected String getDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return omnibusDTO.getTransaction().getFullDomainDTO().getRepositoryPagingDomain();
	}

}
