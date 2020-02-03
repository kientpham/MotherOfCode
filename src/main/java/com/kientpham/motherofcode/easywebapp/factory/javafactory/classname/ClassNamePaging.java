package com.kientpham.motherofcode.easywebapp.factory.javafactory.classname;

import com.kientpham.motherofcode.baseworkflow.BaseOmnibusDTO;
import com.kientpham.motherofcode.easywebapp.model.SharedDTO;
import com.kientpham.motherofcode.easywebapp.model.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public class ClassNamePaging extends ClassNameBuilderBase {
	
	@Override
	public String buildClassForRepositoryPaging(BaseOmnibusDTO<TransactionModel, SharedDTO> omnibusDTO) {
		String entityName=omnibusDTO.getTransaction().getEntity().getName();
		return String.format("\r\npublic interface %1$s extends Repository<%2$s, %3$s> {\r\n\r\n",
				CommonUtils.getObjectNameFromDomain(omnibusDTO.getSharedDTO().getFullDomainDTO(entityName).getRepositoryPagingDomain()), entityName,
				omnibusDTO.getTransaction().getEntity().getFields().get(0).getType().toString())
				+ "\tPage<User> findAll(Pageable pageRequest);\r\n";
	}
}
