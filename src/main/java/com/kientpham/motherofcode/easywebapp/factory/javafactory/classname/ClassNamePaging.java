package com.kientpham.motherofcode.easywebapp.factory.javafactory.classname;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public class ClassNamePaging extends ClassNameBuilderBase {
	
	@Override
	public String buildClassForRepositoryPaging(TransactionModel transaction) {
		return String.format("\r\npublic interface %1$s extends Repository<%2$s, %3$s> {\r\n\r\n",
				CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getRepositoryPagingDomain()), transaction.getEntity().getName(),
				transaction.getEntity().getFields().get(0).getType().toString())
				+ "\tPage<User> findAll(Pageable pageRequest);\r\n";
	}
}
