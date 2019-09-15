package com.kientpham.motherofcode.easywebapp.factory.javafactory.classname;

import com.kientpham.motherofcode.easywebapp.factory.ClassNameInterface;
import com.kientpham.motherofcode.easywebapp.factory.javafactory.JavaConst;
import com.kientpham.motherofcode.easywebapp.workflow.TransactionModel;
import com.kientpham.motherofcode.utils.CommonUtils;

public class ClassNameBuilderBase implements ClassNameInterface {

	@Override
	public String buildClassForPagingInput(TransactionModel transaction) {	
		return getsetAnnotated()+classNameWithGeneric(CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getPagingInput()));
	}

	@Override
	public String buildClassForPagingOutput(TransactionModel transaction) {
		return getsetAnnotated() +classNameWithGeneric(CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getPagingOutput()));
	}
	
	@Override
	public String buildClassForEntity(TransactionModel transaction) {

		return String.format("@Entity\r\n" + "@Table(name = \"%1$s\")\r\n", transaction.getEntity().getTable())
				+ getsetAnnotated() + className(transaction.getEntity().getName());
	}

	@Override
	public String buildClassForRepository(TransactionModel transaction) {
		return String.format("\r\npublic interface %1$s extends CrudRepository<%2$s, %3$s> {\r\n",
				CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getRepositoryDomain()), transaction.getEntity().getName(),
				transaction.getEntity().getFields().get(0).getType().toString());
	}
	
	@Override
	public String buildClassForRepositoryPaging(TransactionModel transaction) {
		return "";
	}

	@Override
	public String buildClassForDBGatewayInterface(TransactionModel transaction) {
		return buildInterfaceName(CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getDbGatewayInterface()));
	}

	@Override
	public String buildClassForDBGateway(TransactionModel transaction) {
		return JavaConst.COMPONENTANNOTATED + buildClassNameImplements(CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getDbGateway()),
				CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getDbGatewayInterface()));
	}

	@Override
	public String buildClassForBusinessObject(TransactionModel transaction) {
		return JavaConst.COMPONENTANNOTATED + className(CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getBussinessDomain()));
	}

	@Override
	public String buildClassForServiceInterface(TransactionModel transaction) {

		return buildInterfaceName(CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getServiceInterfaceDomain()));
	}

	@Override
	public String buildClassForServiceClass(TransactionModel transaction) {
		return JavaConst.COMPONENTANNOTATED
				+ buildClassNameImplements(CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getServiceDomain()),
						CommonUtils.getObjectNameFromDomain(transaction.getFullDomainDTO().getServiceInterfaceDomain()));
	}

	private String getsetAnnotated() {
		return "@Getter\r\n" + "@Setter\r\n";
	}

	private String className(String className) {
		return String.format("\r\npublic class %1$s", className).trim() + "{\r\n";
	}
	
	private String classNameWithGeneric(String className) {
		return String.format("\r\npublic class %1$s<T>", className).trim() + "{\r\n";
	}

	private String buildInterfaceName(String className) {
		return String.format("\r\npublic interface %1$s", className).trim() + "{\r\n";
	}

	private String buildClassNameImplements(String className, String parent) {
		return String.format("\r\npublic class %1$s implements %2$s", className, parent).trim() + "{\r\n";
	}

}
