package com.kientpham.motherofcode.easywebapp.workflow.factory.classname;

import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.easywebapp.workflow.factory.ClassNameInterface;

public class ClassNamePaging implements ClassNameInterface {

	@Override
	public String buildClassForEntity(TransactionModel transaction) {
		return "";
	}

	@Override
	public String buildClassForRepository(TransactionModel transaction) {
		return String.format("\r\npublic interface %1$s extends Repository<%2$s, %3$s> {\r\n\r\n",
				transaction.getFullDomainDTO().getRepositoryPagingDomain(), transaction.getEntity().getName(),
				transaction.getEntity().getFields().get(0).getType().toString())
				+ "\tPage<User> findAll(Pageable pageRequest);\r\n";
	}

	@Override
	public String buildClassForDBGateway(TransactionModel transaction) {
		return "";
	}

	@Override
	public String buildClassForDBGatewayInterface(TransactionModel transaction) {
		return "";
	}

	@Override
	public String buildClassForBusinessObject(TransactionModel transaction) {
		return "";
	}

	@Override
	public String buildClassForServiceInterface(TransactionModel transaction) {
		return "";
	}

	@Override
	public String buildClassForServiceClass(TransactionModel transaction) {
		// TODO Auto-generated method stub
		return null;
	}

}
