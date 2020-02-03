package com.kientpham.motherofcode.easywebapp.factory.servicebuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.EntityBaseBuilder;
import com.kientpham.motherofcode.easywebapp.factory.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;

public class ReadServiceImplBuilder extends EntityBaseBuilder{

	@Override
	protected String getImportCode(ImportLibInterface importLibBuilder, BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return importLibBuilder.importForReadServiceImpl(omnibusDTO);
	}

	@Override
	protected String getClassName(ClassNameInterface classNameBuilder, BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {	
		return classNameBuilder.buildClassForReadServiceImpl(omnibusDTO);
	}

	@Override
	protected String getMethodCode(MethodBuilderInterface methodBuilder, BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return methodBuilder.buildMethodForReadServiceImpl(omnibusDTO);
	}

	@Override
	protected String getDomain(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		return omnibusDTO.getTransaction().getFullDomainDTO().getReadServiceImpl();
	}

}
