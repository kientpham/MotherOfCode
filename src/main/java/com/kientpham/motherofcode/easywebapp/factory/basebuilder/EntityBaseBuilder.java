package com.kientpham.motherofcode.easywebapp.factory.basebuilder;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.ImportLibInterface;
import com.kientpham.motherofcode.easywebapp.factory.interfaces.MethodBuilderInterface;
import com.kientpham.motherofcode.easywebapp.model.CodeFactory;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.Const;

public abstract class EntityBaseBuilder extends AbstractBuilder{

	abstract protected String getImportCode(ImportLibInterface importLibBuilder, BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO);

	abstract protected String getClassName(ClassNameInterface classNameBuilder, BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO);
	
	abstract protected String getMethodCode(MethodBuilderInterface methodBuilder, BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO);	

	@Override
	protected String generateCode(BaseOmnibusDTO<TransactionModel,SharedDTO> omnibusDTO) {		
		if (omnibusDTO.getTransaction().getFullDomainDTO()==null)
			omnibusDTO.getTransaction().setFullDomainDTO(omnibusDTO.getSharedDTO().getFullDomainDTO(omnibusDTO.getTransaction().getEntity().getName()));
		
		 CodeFactory codeFactory=omnibusDTO.getTransaction().getCodeFactory();
		StringBuilder codeBody=new StringBuilder();
		for (ImportLibInterface importLibBuilder:codeFactory.getImportLibBuilderList()) {
			codeBody.append(getImportCode(importLibBuilder, omnibusDTO)+Const.RETURN);
		}
		for (ClassNameInterface classNameBuilder: codeFactory.getClassNameBuilderList()) {
			codeBody.append(getClassName(classNameBuilder, omnibusDTO));
		}		
		for (MethodBuilderInterface methodBuilder:codeFactory.getMethodBuilderList()) {
			codeBody.append(getMethodCode(methodBuilder, omnibusDTO));
		}		
		return codeBody.toString();
	}
}
